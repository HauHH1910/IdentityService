package com.hauhh.services.impl;

import com.hauhh.controllers.request.AuthenticationRequest;
import com.hauhh.controllers.request.IntrospectRequest;
import com.hauhh.controllers.request.LogoutRequest;
import com.hauhh.controllers.request.RefreshRequest;
import com.hauhh.controllers.response.AuthenticationResponse;
import com.hauhh.controllers.response.IntrospectResponse;
import com.hauhh.controllers.response.RefreshResponse;
import com.hauhh.models.InvalidateToken;
import com.hauhh.models.User;
import com.hauhh.models.enums.ErrorConstant;
import com.hauhh.exceptions.BusinessException;
import com.hauhh.repositories.InvalidateTokenRepository;
import com.hauhh.repositories.UserRepository;
import com.hauhh.services.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvalidateTokenRepository tokenRepository;

    @NonFinal
    @Value("${com.hauhh.key}")
    private String KEY;

    @NonFinal
    @Value("${com.hauhh.valid-duration}")
    private long VALID_DURATION;

    @NonFinal
    @Value("${com.hauhh.refreshable-duration}")
    private long REFRESHABLE_DURATION;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new BusinessException(ErrorConstant.USER_NOT_EXIST));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new BusinessException(ErrorConstant.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        log.info("User {}", user.getUsername());
        log.info("User Role: {}", user.getRoles());
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    //Verify token
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (BusinessException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jwtID = signToken.getJWTClaimsSet().getJWTID();

            Date expirationDate = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .tokenID(jwtID)
                    .expiryTime(expirationDate)
                    .build();

            tokenRepository.save(invalidateToken);

        } catch (BusinessException e) {
            log.info("Token already expired");
        }

    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        //Kiểm tra xem token còn hiệu lực không
        var signJWT = verifyToken(request.getToken(), true);

        var jit = signJWT.getJWTClaimsSet().getJWTID();

        var expirationDate = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .tokenID(jit)
                .expiryTime(expirationDate)
                .build();

        tokenRepository.save(invalidateToken);

        var username = signJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorConstant.UNAUTHENTICATED));

        var token = generateToken(user);

        return RefreshResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //Hết hạn sau 1 giờ
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.hauhh")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(KEY.getBytes()));
        } catch (JOSEException e) {
            log.error("Error at [AuthenticationServiceImpl] - method [generateToken] {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {

        JWSVerifier verifier = new MACVerifier(KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifyToken = signedJWT.verify(verifier);

        if (!(verifyToken && expirationTime.after(new Date())))
            throw new BusinessException(ErrorConstant.UNAUTHENTICATED);

        if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new BusinessException(ErrorConstant.UNAUTHENTICATED);

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}

package com.hauhh.configurations;

import com.hauhh.controllers.request.IntrospectRequest;
import com.hauhh.models.enums.ErrorCode;
import com.hauhh.exceptions.AppException;
import com.hauhh.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJWTDecoder implements JwtDecoder {

    @Value("${com.hauhh.key}")
    private String key;

    private final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authenticationService.introspect(IntrospectRequest.builder()
                    .token(token)
                    .build());
            if(!response.isValid()){
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }


        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}

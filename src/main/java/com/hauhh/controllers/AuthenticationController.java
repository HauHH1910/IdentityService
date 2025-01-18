package com.hauhh.controllers;

import com.hauhh.commons.ResponseData;
import com.hauhh.configurations.Translator;
import com.hauhh.controllers.request.AuthenticationRequest;
import com.hauhh.controllers.request.IntrospectRequest;
import com.hauhh.controllers.request.LogoutRequest;
import com.hauhh.controllers.request.RefreshRequest;
import com.hauhh.controllers.response.AuthenticationResponse;
import com.hauhh.controllers.response.IntrospectResponse;
import com.hauhh.controllers.response.RefreshResponse;
import com.hauhh.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(
            summary = "Authentication",
            description = "Login to the account using JWT"
    )
    @PostMapping("/token")
    public ResponseData<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("Attempting to authenticate user: {}", request.getUsername());
        log.info("Attempting to authenticate user password: {}", request.getPassword());
        return ResponseData.<AuthenticationResponse>builder()
                .message(Translator.toLocale("auth.login"))
                .result(authenticationService.authenticate(request))
                .build();
    }

    @Operation(
            summary = "Introspect JWT token ",
            description = "Check if the JWT token is valid or not"
    )
    @PostMapping("/introspect")
    public ResponseData<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ResponseData.<IntrospectResponse>builder()
                .message(Translator.toLocale("auth.introspect"))
                .result(authenticationService.introspect(request))
                .build();
    }

    @Operation(
            summary = "Logout",
            description = "Logout from the account"
    )
    @PostMapping("/logout")
    public ResponseData<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseData.<Void>builder()
                .message(Translator.toLocale("auth.logout"))
                .build();
    }

    @Operation(
            summary = "Refresh JWT token",
            description = "Refresh JWT token"
    )
    @PostMapping("/refresh")
    public ResponseData<RefreshResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return ResponseData.<RefreshResponse>builder()
                .message(Translator.toLocale("auth.refresh"))
                .result(authenticationService.refreshToken(request))
                .build();
    }

}

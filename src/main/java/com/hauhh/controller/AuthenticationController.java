package com.hauhh.controller;

import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.AuthenticationRequest;
import com.hauhh.dto.request.IntrospectRequest;
import com.hauhh.dto.request.LogoutRequest;
import com.hauhh.dto.response.AuthenticationResponse;
import com.hauhh.dto.response.IntrospectResponse;
import com.hauhh.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseData<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseData.<AuthenticationResponse>builder()
                .message("Login successful")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ResponseData<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ResponseData.<IntrospectResponse>builder()
                .message("Introspect successful")
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ResponseData<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseData.<Void>builder()
                .message("Logout successful")
                .build();
    }

}

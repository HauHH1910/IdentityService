package com.hauhh.controller;

import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.AuthenticationRequest;
import com.hauhh.dto.response.AuthenticateResponse;
import com.hauhh.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseData<AuthenticateResponse> login(@RequestBody AuthenticationRequest request) {
        boolean authenticated = authenticationService.authenticate(request);
        return ResponseData.<AuthenticateResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(AuthenticateResponse.builder()
                        .authenticated(authenticated)
                        .build())
                .build();
    }

}

package com.hauhh.service;

import com.hauhh.dto.request.AuthenticationRequest;

public interface AuthenticationService {

    boolean authenticate(AuthenticationRequest authenticationRequest);
}

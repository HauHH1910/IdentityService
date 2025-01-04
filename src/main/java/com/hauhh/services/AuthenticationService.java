package com.hauhh.services;

import com.hauhh.controllers.request.AuthenticationRequest;
import com.hauhh.controllers.request.IntrospectRequest;
import com.hauhh.controllers.request.LogoutRequest;
import com.hauhh.controllers.request.RefreshRequest;
import com.hauhh.controllers.response.AuthenticationResponse;
import com.hauhh.controllers.response.IntrospectResponse;
import com.hauhh.controllers.response.RefreshResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}

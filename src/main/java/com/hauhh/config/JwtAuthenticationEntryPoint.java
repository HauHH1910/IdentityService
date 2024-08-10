package com.hauhh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauhh.common.ResponseData;
import com.hauhh.common.ResponseError;
import com.hauhh.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseData<ResponseError> responseData = ResponseData.<ResponseError>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("JwtAuthenticationEntryPoint: {}", objectMapper.writeValueAsString(responseData));
        response.getWriter().write(objectMapper.writeValueAsString(responseData));

        response.flushBuffer();
    }
}

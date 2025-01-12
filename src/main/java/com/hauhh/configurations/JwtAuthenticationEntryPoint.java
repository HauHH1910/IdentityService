package com.hauhh.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauhh.commons.ResponseData;
import com.hauhh.commons.ResponseError;
import com.hauhh.models.enums.ErrorConstant;
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
        ErrorConstant errorConstant = ErrorConstant.UNAUTHENTICATED;

        response.setStatus(errorConstant.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseData<ResponseError> responseData = ResponseData.<ResponseError>builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("JwtAuthenticationEntryPoint: {}", objectMapper.writeValueAsString(responseData));
        response.getWriter().write(objectMapper.writeValueAsString(responseData));

        response.flushBuffer();
    }
}

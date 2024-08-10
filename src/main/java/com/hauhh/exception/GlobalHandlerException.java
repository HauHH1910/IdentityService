package com.hauhh.exception;

import com.hauhh.common.ResponseData;
import com.hauhh.common.ResponseError;
import com.hauhh.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseError> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseError.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return ResponseEntity.status(ErrorCode.ACCESS_DENIED.getStatusCode())
                .body(ResponseError.builder()
                        .code(ErrorCode.ACCESS_DENIED.getCode())
                        .message(ErrorCode.ACCESS_DENIED.getMessage())
                        .build());
    }

}

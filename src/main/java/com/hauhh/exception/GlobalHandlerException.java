package com.hauhh.exception;

import com.hauhh.common.ResponseData;
import com.hauhh.common.ResponseError;
import com.hauhh.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseError> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        return ResponseEntity.badRequest().body(ResponseError.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

}

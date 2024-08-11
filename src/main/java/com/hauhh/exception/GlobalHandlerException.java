package com.hauhh.exception;

import com.hauhh.common.ResponseError;
import com.hauhh.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ResponseError> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        log.info("ErrorCode Code: {}", errorCode.getCode());
        log.info("ErrorCode Message: {}", errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseError.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException() {
        return ResponseEntity.status(ErrorCode.ACCESS_DENIED.getStatusCode())
                .body(ResponseError.builder()
                        .code(ErrorCode.ACCESS_DENIED.getCode())
                        .message(ErrorCode.ACCESS_DENIED.getMessage())
                        .build());
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleSQLIntegrityConstraintViolationException() {
        return ResponseEntity.status(ErrorCode.PERMISSION_IS_USED.getStatusCode())
                .body(ResponseError.builder()
                        .code(ErrorCode.PERMISSION_IS_USED.getCode())
                        .message(ErrorCode.PERMISSION_IS_USED.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        log.info("ErrorCode Code: {}", errorCode.getCode());
        log.info("ErrorCode Message: {}", errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseError.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }


}

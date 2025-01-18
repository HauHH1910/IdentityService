package com.hauhh.exceptions;

import com.hauhh.commons.ResponseError;
import com.hauhh.configurations.Translator;
import com.hauhh.models.enums.ErrorConstant;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ResponseError> handleAppException(BusinessException businessException) {
        ErrorConstant errorConstant = businessException.getErrorConstant();
        log.info("[handleAppException] Code: {}", errorConstant.getCode());
        log.info("[handleAppException] Message: {}", errorConstant.getMessage());
        return ResponseEntity.status(errorConstant.getStatusCode())
                .body(ResponseError.builder()
                        .code(errorConstant.getCode())
                        .message(Translator.toLocale(errorConstant.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException() {
        return ResponseEntity.status(ErrorConstant.ACCESS_DENIED.getStatusCode())
                .body(ResponseError.builder()
                        .code(ErrorConstant.ACCESS_DENIED.getCode())
                        .message(Translator.toLocale(ErrorConstant.ACCESS_DENIED.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleSQLIntegrityConstraintViolationException() {
        return ResponseEntity.status(ErrorConstant.PERMISSION_IS_USED.getStatusCode())
                .body(ResponseError.builder()
                        .code(ErrorConstant.PERMISSION_IS_USED.getCode())
                        .message(Translator.toLocale(ErrorConstant.PERMISSION_IS_USED.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorConstant errorConstant = ErrorConstant.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorConstant = ErrorConstant.valueOf(enumKey);


            var constraintViolation = exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {
            log.error("[handleValidation] Error IllegalArgumentException: {}", e.getMessage());
        }

        log.info("[handleValidation] Code: {}", errorConstant.getCode());
        log.info("[handleValidation] Message: {}", errorConstant.getMessage());

        return ResponseEntity.status(errorConstant.getStatusCode())
                .body(ResponseError.builder()
                        .code(errorConstant.getCode())
                        .message(
                                Translator.toLocale(
                                        Objects.nonNull(attributes) ?
                                                mapAttribute(errorConstant.getMessage(), attributes)
                                                :
                                                errorConstant.getMessage()
                                )
                        )
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

}

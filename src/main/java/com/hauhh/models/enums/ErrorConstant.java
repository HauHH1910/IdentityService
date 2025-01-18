package com.hauhh.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public enum ErrorConstant {
    USER_EXIST(1001, "user.exist", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002, "user.not.exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(1004, "uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1005, "access.denied", HttpStatus.FORBIDDEN),
    PERMISSION_IS_USED(1006, "permission.is.used", HttpStatus.CONFLICT),
    INVALID_DOB(1007, "Your age must be at least {min}", HttpStatus.FORBIDDEN),
    INVALID_KEY(1008, "invalid.key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1009, "Username must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(10010, "Password must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(10011, "invalid.token", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(10012, "role.not.found", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

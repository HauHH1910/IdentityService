package com.hauhh.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public enum ErrorConstant {
    USER_EXIST(1001, "user.exist", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002, "User does not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(1004, "Unauthorized", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1005, "Access denied", HttpStatus.FORBIDDEN),
    PERMISSION_IS_USED(1006, "Permission is used", HttpStatus.CONFLICT),
    INVALID_DOB(1007, "Your age must be at least {min}", HttpStatus.FORBIDDEN),
    INVALID_KEY(1008, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1009, "Username must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(10010, "Password must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(10011, "Invalid token", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(10012, "Role not found", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

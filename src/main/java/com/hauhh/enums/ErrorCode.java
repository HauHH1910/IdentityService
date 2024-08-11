package com.hauhh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXIST(1001, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002, "User does not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(1004, "Unauthorized", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1005, "Access denied", HttpStatus.FORBIDDEN),
    PERMISSION_IS_USED(1006, "Permission is used", HttpStatus.CONFLICT),
    INVALID_DOB(1007, "Invalid date of birth", HttpStatus.FORBIDDEN),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

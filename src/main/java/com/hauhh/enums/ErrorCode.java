package com.hauhh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXIST(1001, "User already exists"),
    USER_NOT_EXIST(1002, "User does not exist"),
    UNAUTHENTICATED(1003, "Unauthenticated"),
    ;
    private final int code;
    private final String message;
}

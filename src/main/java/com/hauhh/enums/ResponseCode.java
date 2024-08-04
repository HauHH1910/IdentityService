package com.hauhh.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(1, "Success"),
    ;

    private int code;
    private String message;
}

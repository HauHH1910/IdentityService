package com.hauhh.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private int code;

    private String message;

    private T result;

}

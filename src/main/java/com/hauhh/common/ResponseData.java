package com.hauhh.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    @Builder.Default
    private int code = 1000;

    private String message;

    private T result;

}

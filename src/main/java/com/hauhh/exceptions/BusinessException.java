package com.hauhh.exceptions;

import com.hauhh.models.enums.ErrorConstant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException{

    private final ErrorConstant errorConstant;

    public BusinessException(ErrorConstant errorConstant) {
        super(errorConstant.getMessage());
        this.errorConstant = errorConstant;
    }

}

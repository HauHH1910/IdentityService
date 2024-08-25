package com.hauhh.constraint;

import com.hauhh.validator.DoBValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })// Validate ở field
@Retention(RUNTIME) // Xử lý khi nào ở đây hiện tại là runtime
@Constraint(validatedBy = { DoBValidator.class })
public @interface DoBConstraint {

    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

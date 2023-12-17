package com.example.registration.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

    String message() default
            "Must be more than 8 characters long and must contain at least 1 uppercase letter," +
                    " 1 digit, and 1 special character from the set of { '_', '#', '$', '%', '.' }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

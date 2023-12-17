package com.example.registration.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CanadaIPAddressValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IPAddressValidator {

    String message() default
            "Must be a valid IPV4 address from within Canada.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

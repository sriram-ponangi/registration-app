package com.example.registration.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CanadaIPAddressValidator implements ConstraintValidator<IPAddressValidator, String> {

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext constraintValidatorContext) {
        return ipAddress.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

}

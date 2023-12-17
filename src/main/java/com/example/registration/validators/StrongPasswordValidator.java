package com.example.registration.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<PasswordValidator, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[_#$%.]).{9,}$");
    }
}

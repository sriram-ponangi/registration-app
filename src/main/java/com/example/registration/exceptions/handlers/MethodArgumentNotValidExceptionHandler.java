package com.example.registration.exceptions.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    private final String appUrl;

    public MethodArgumentNotValidExceptionHandler(@Value("${app.url}") String appUrl) {
        this.appUrl = appUrl;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add("Validation failed for " + fieldName + ". " + errorMessage);
        });

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create(appUrl+"/swagger-ui/index.html#/user-controller/registerVersion"));
        pd.setTitle("Request validation failed.");
        pd.setProperty("errorMessages", errors);

        return pd;
    }


}

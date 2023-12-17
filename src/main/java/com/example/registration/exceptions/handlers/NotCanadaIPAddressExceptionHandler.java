package com.example.registration.exceptions.handlers;

import com.example.registration.exceptions.NotCanadaIPAddressException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class NotCanadaIPAddressExceptionHandler {
    private final String appUrl;

    public NotCanadaIPAddressExceptionHandler(@Value("${app.url}") String appUrl) {
        this.appUrl = appUrl;
    }

    @ExceptionHandler(NotCanadaIPAddressException.class)
    public ProblemDetail handleNotCanadaIPAddressExceptions(NotCanadaIPAddressException ex) {
        List<String> errors = new ArrayList<>();
        errors.add("Validation failed for ipAddress. " + ex.getMessage());

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create(appUrl + "/swagger-ui/index.html#/user-controller/registerVersion"));
        pd.setTitle("Request validation failed.");
        pd.setProperty("errorMessages", errors);

        return pd;
    }
}

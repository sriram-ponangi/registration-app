package com.example.registration.exceptions.handlers;

import com.example.registration.exceptions.IPAPIInvocationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class IPAPIInvocationExceptionHandler {
    private final String appUrl;
    public IPAPIInvocationExceptionHandler(@Value("${app.url}") String appUrl) {
        this.appUrl = appUrl;
    }

    @ExceptionHandler(IPAPIInvocationException.class)
    public ProblemDetail handleIPAPIInvocationExceptions(IPAPIInvocationException ex) {
        List<String> errors = new ArrayList<>();
        errors.add("Unable to validate ipAddress. " + ex.getMessage());

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create(appUrl+"/swagger-ui/index.html#/user-controller/registerVersion"));
        pd.setTitle("Request validation failed.");
        pd.setProperty("errorMessages", errors);

        return pd;
    }
}

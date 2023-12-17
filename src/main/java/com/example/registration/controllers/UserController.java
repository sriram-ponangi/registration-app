package com.example.registration.controllers;

import com.example.registration.models.RegisterRequest;
import com.example.registration.models.RegisterResponse;
import com.example.registration.services.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final RegisterService registerService;

    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(
            description = "Register user based on the provided details and generate a uuid for the user.",
            method = "register"
    )
    @ApiResponse(responseCode = "201", description = "Registered user successfully.",
            content = @Content(
                    schema = @Schema(implementation = RegisterResponse.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @ApiResponse(responseCode = "400", description = "Invalid data provided for user registration.",
            content = @Content(
                    schema = @Schema(implementation = ProblemDetail.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @ApiResponse(responseCode = "500", description = "Internal Server Error. Failed to register user.",
            content = @Content(
                    schema = @Schema(implementation = ProblemDetail.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @PostMapping("/register")
    @ResponseBody
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest,
                                                     @RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerService.register(registerRequest));
    }
}

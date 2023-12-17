package com.example.registration.models;

import com.example.registration.validators.IPAddressValidator;
import com.example.registration.validators.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @Schema(
            name = "username",
            description = "Provide a valid username that is not blank."
    )
    @NotBlank(message = "Must not be blank")
    @JsonProperty("username")
    String username;

    @Schema(
            name = "password",
            description = "Password needs to be greater than 8 characters, containing at least 1 number, " +
                    "1 uppercase letter, 1 special character from the set (_ # $ % .)",
            pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[_#$%.]).{9,}$"
    )
    @PasswordValidator
    @JsonProperty("password")
    String password;


    @Schema(
            name = "ipAddress",
            format = "ipv4",
            description = "Provide a valid IPV4 address from within Canada."
    )
    @IPAddressValidator
    @JsonProperty("ipAddress")
    String ipAddress;
}

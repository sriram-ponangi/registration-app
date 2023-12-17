package com.example.registration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    @Schema(
            name = "message",
            description = "Welcome message with username and the city name found from the given IP."
    )
    @JsonProperty("message")
    String message;

    @Schema(
            name = "id",
            description = "Randomly generated UUID."
    )
    @JsonProperty("id")
    String id;

}

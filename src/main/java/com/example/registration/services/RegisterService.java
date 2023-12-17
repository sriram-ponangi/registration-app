package com.example.registration.services;

import com.example.registration.exceptions.IPAPIInvocationException;
import com.example.registration.exceptions.NotCanadaIPAddressException;
import com.example.registration.models.RegisterRequest;
import com.example.registration.models.RegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RegisterService {

    private final RestTemplate restTemplate;
    private final String ipValidationApi;

    public RegisterService(RestTemplate restTemplate,
                           @Value("${ip-validation-api}") String ipValidationApi) {
        this.restTemplate = restTemplate;
        this.ipValidationApi = ipValidationApi;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("[STARTING] :: Verifying if the given IP address is from Canada.");

        Map<String, String> response;
        try {
            response = this.restTemplate.getForObject(
                    ipValidationApi + registerRequest.getIpAddress()
                            + "?fields=status,country,city", Map.class);

            log.debug("[PROCESSING] :: IPV4 verification API response: {}", response);
            if (response == null || response.isEmpty()) {
                throw new IPAPIInvocationException("IP API response is null or empty.");
            }
        } catch (Exception e) {
            log.error("[FAILED] :: Unable to get the city name for the ipAddress: {}", registerRequest.getIpAddress(), e);
            throw new IPAPIInvocationException("Could not verify ipAddress "
                    + registerRequest.getIpAddress() + ", please try again later.");
        }

        if (StringUtils.equals("success", response.get("status"))
                && StringUtils.equals("Canada", response.get("country"))
                && StringUtils.isNotBlank(response.get("city"))) {
            log.info("[SUCCESS] :: Registering the user is completed.");
            return new RegisterResponse("Hello " + registerRequest.getUsername() + " from " + response.get("city")
                    + " your account is registered successfully.",
                    UUID.randomUUID().toString());
        }

        log.info("[FAILED] :: The received IP address {} is not from Canada.", registerRequest.getIpAddress());

        throw new NotCanadaIPAddressException("The given IPV4 address " + registerRequest.getIpAddress()
                + " is not from Canada. The country is " +
                response.getOrDefault("country", "unknown")
                + " and the city is " + response.getOrDefault("city", "unknown")+". You are not eligible to register.");
    }
}

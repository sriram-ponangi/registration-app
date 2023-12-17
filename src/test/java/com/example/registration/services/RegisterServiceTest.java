package com.example.registration.services;

import com.example.registration.exceptions.IPAPIInvocationException;
import com.example.registration.exceptions.NotCanadaIPAddressException;
import com.example.registration.models.RegisterRequest;
import com.example.registration.models.RegisterResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private RegisterService registerService;

    private static String ipValidationApiMock;

    private static RegisterRequest registerRequest;

    @BeforeAll
    static void setup() {
        registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1");

        ipValidationApiMock = null;

    }

    @Test
    void whenIPAPIResponseIsNULL_register_test() {
        ReflectionTestUtils.setField(registerService, "ipValidationApi", ipValidationApiMock);

        Mockito.lenient().when(restTemplateMock.getForObject(
                        ipValidationApiMock + registerRequest.getIpAddress() + "?fields=status,country,city", Map.class))
                .thenReturn(null);

        IPAPIInvocationException thrownException = Assertions.assertThrows(IPAPIInvocationException.class,
                () -> registerService.register(registerRequest));

        AssertionErrors.assertEquals("Invalid message thrown with IPAPIInvocationException.",
                "Could not verify ipAddress " + registerRequest.getIpAddress() + ", please try again later.",
                thrownException.getMessage());
    }

    @Test
    void whenIPAPIResponseStatusIsFail_register_test() {
        Map<String, String> response = new HashMap<>();
        response.put("query", registerRequest.getIpAddress());
        response.put("message", "reserved range");
        response.put("status", "fail");

        Mockito.lenient().when(restTemplateMock.getForObject(
                        ipValidationApiMock + registerRequest.getIpAddress() + "?fields=status,country,city", Map.class))
                .thenReturn(response);

        NotCanadaIPAddressException thrownException = Assertions.assertThrows(NotCanadaIPAddressException.class,
                () -> registerService.register(registerRequest));

        AssertionErrors.assertEquals("Invalid message thrown with NotCanadaIPAddressException.",
                "The given IPV4 address " + registerRequest.getIpAddress() +
                        " is not from Canada. The country is unknown and the city is unknown. You are not eligible to register.",
                thrownException.getMessage());
    }

    @Test
    void whenIPAPIResponseCountryIsNotCanada_register_test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("country", "Australia");
        response.put("city", "South Brisbane");

        Mockito.lenient().when(restTemplateMock.getForObject(
                        ipValidationApiMock + registerRequest.getIpAddress() + "?fields=status,country,city", Map.class))
                .thenReturn(response);

        NotCanadaIPAddressException thrownException = Assertions.assertThrows(NotCanadaIPAddressException.class,
                () -> registerService.register(registerRequest));

        AssertionErrors.assertEquals("Invalid message thrown with NotCanadaIPAddressException.",
                "The given IPV4 address " + registerRequest.getIpAddress() +
                        " is not from Canada. The country is Australia and the city is South Brisbane. You are not eligible to register.",
                thrownException.getMessage());
    }

    @Test
    void whenIPAPIResponseCountryIsCanada_register_test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("country", "Canada");
        response.put("city", "Montreal");

        Mockito.lenient().when(restTemplateMock.getForObject(
                        ipValidationApiMock + registerRequest.getIpAddress() + "?fields=status,country,city", Map.class))
                .thenReturn(response);


        RegisterResponse registerResponse = registerService.register(registerRequest);

        AssertionErrors.assertEquals("Invalid message generated when registration is successful.",
                "Hello " + registerRequest.getUsername() + " from " + response.get("city")
                        + " your account is registered successfully.",
                registerResponse.getMessage()
        );

        AssertionErrors.assertEquals("Invalid UUID generated when registration is successful.",
                UUID.fromString(registerResponse.getId()).toString(), registerResponse.getId());
    }
}

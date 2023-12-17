package com.example.registration.controllers;

import com.example.registration.exceptions.IPAPIInvocationException;
import com.example.registration.exceptions.NotCanadaIPAddressException;
import com.example.registration.models.RegisterRequest;
import com.example.registration.models.RegisterResponse;
import com.example.registration.services.RegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    private final MockMvc mockMvc;
    @MockBean
    RegisterService registerServiceMock;

    public UserControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void whenRequestIsValid_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1");

        Mockito.when(registerServiceMock.register(registerRequest))
                .thenReturn(new RegisterResponse("test message", "test id"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("test message"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("test id"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidPassword_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#"); //Invalid password due to missing digit
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]").value("Validation failed for password. Must be more than 8 characters long and must contain at least 1 uppercase letter, 1 digit, and 1 special character from the set of { '_', '#', '$', '%', '.' }"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidIPAddress_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1234"); //Invalid IPV4 address

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]").value("Validation failed for ipAddress. Must be a valid IPV4 address from within Canada."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidUsername_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername(" "); //Invalid username
        registerRequest.setIpAddress("1.1.1.123");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]").value("Validation failed for username. Must not be blank"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidUsernameAndPassword_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#"); //Invalid password due to missing digit
        registerRequest.setUsername(" "); //Invalid username
        registerRequest.setIpAddress("1.1.1.123");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages",
                        Matchers.containsInAnyOrder("Validation failed for username. Must not be blank",
                                "Validation failed for password. Must be more than 8 characters long and must contain at least 1 uppercase letter, 1 digit, and 1 special character from the set of { '_', '#', '$', '%', '.' }")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidUsernameAndIPAddress_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1"); //Invalid password due to missing digit
        registerRequest.setUsername(" "); //Invalid username
        registerRequest.setIpAddress("1.1.1.1234"); //Invalid IPV4 Address

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages",
                        Matchers.containsInAnyOrder("Validation failed for username. Must not be blank",
                                "Validation failed for ipAddress. Must be a valid IPV4 address from within Canada.")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenRequestHasInvalidUsernamePasswordAndIPAddress_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#");
        registerRequest.setUsername(" "); //Invalid username
        registerRequest.setIpAddress("1.1.1.1234"); //Invalid IPV4 Address

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages",
                        Matchers.containsInAnyOrder("Validation failed for username. Must not be blank",
                                "Validation failed for ipAddress. Must be a valid IPV4 address from within Canada.",
                                "Validation failed for password. Must be more than 8 characters long and must contain at least 1 uppercase letter, 1 digit, and 1 special character from the set of { '_', '#', '$', '%', '.' }")))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void whenNotCanadaIPAddressExceptionIsThrown_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1");

        Mockito.when(registerServiceMock.register(registerRequest))
                .thenThrow(new NotCanadaIPAddressException(
                        "The given IPV4 address 1.1.1.1 is not from Canada. The country is TestCountry and the city is TestCity. You are not eligible to register."
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]")
                        .value("Validation failed for ipAddress. The given IPV4 address 1.1.1.1 is not from Canada." +
                        " The country is TestCountry and the city is TestCity. You are not eligible to register."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void whenIPAPIInvocationExceptionIsThrown_register_test() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("TestPassWord#1");
        registerRequest.setUsername("TestUsername");
        registerRequest.setIpAddress("1.1.1.1");

        Mockito.when(registerServiceMock.register(registerRequest))
                .thenThrow(new IPAPIInvocationException(
                        "Could not verify ipAddress 1.1.1.1, please try again later."
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Request validation failed."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instance").value("/v1/user/register"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("http://localhost:8089/api/swagger-ui/index.html#/user-controller/registerVersion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]").value("Unable to validate ipAddress. Could not verify ipAddress 1.1.1.1, please try again later."))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String getJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

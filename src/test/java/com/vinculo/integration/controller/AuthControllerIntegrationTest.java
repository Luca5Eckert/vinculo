package com.vinculo.integration.controller;

import com.vinculo.module.auth.application.dto.LoginRequest;
import com.vinculo.module.auth.application.dto.RegisterPersonRequest;
import com.vinculo.module.auth.application.handler.LoginHandler;
import com.vinculo.module.auth.application.handler.RegisterPersonHandler;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.vinculo.module.auth.application.controller.AuthController;
import com.vinculo.share.security.SecurityConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AuthController.
 * Tests the authentication endpoints including registration and login.
 * Uses WebMvcTest to test the controller layer with mocked dependencies.
 */
@WebMvcTest(AuthController.class)
@Import(SecurityConfiguration.class)
@DisplayName("Auth Controller Integration Tests")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterPersonHandler registerPersonHandler;

    @MockBean
    private LoginHandler loginHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should successfully register a new person")
    void shouldRegisterNewPerson() throws Exception {
        // Arrange
        RegisterPersonRequest request = new RegisterPersonRequest(
                "John Doe",
                "john.doe.test@example.com",
                "+5511999999999",
                "securePassword123"
        );

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should successfully login with valid credentials")
    void shouldLoginWithValidCredentials() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest(
                "jane.smith.test@example.com",
                "myPassword456"
        );

        when(loginHandler.handle(any(LoginRequest.class)))
                .thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Should return 400 when login request has missing fields")
    void shouldReturnBadRequestForMissingLoginFields() throws Exception {
        // Arrange - Empty email and password
        LoginRequest request = new LoginRequest("", "");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

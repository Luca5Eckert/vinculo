package com.vinculo.integration.controller;

import com.vinculo.module.connection.application.controller.ConnectionController;
import com.vinculo.module.connection.application.dto.ConnectionResponse;
import com.vinculo.module.connection.application.handler.GetMyConnectionsHandler;
import com.vinculo.share.security.SecurityConfiguration;
import com.vinculo.share.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ConnectionController.
 * Tests getting user connections.
 */
@WebMvcTest(ConnectionController.class)
@Import(SecurityConfiguration.class)
@DisplayName("Connection Controller Integration Tests")
class ConnectionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetMyConnectionsHandler getMyConnectionsHandler;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @WithMockUser
    @DisplayName("Should return empty list when user has no connections")
    void shouldReturnEmptyListWithNoConnections() throws Exception {
        // Arrange
        when(authenticationService.getAuthenticatedPersonId()).thenReturn("user-1");
        when(getMyConnectionsHandler.handle(anyString())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/v1/connections/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Should return 401 when getting connections without authentication")
    void shouldReturnUnauthorizedWithoutAuth() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/connections/me"))
                .andExpect(status().isUnauthorized());
    }
}

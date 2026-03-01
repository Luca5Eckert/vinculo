package com.vinculo.integration.controller;

import com.vinculo.module.connection.domain.model.TypeConnection;
import com.vinculo.module.request_connection.application.controller.RequestConnectionController;
import com.vinculo.module.request_connection.application.dto.SendRequestConnectionRequest;
import com.vinculo.module.request_connection.application.handler.GetMyRequestConnectionsHandler;
import com.vinculo.module.request_connection.application.handler.SendRequestConnectionHandler;
import com.vinculo.module.request_connection.application.handler.UpdateStatusRequestConnectionHandler;
import com.vinculo.share.security.SecurityConfiguration;
import com.vinculo.share.service.AuthenticationService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for RequestConnectionController.
 * Tests connection request sending, status updates, and retrieval.
 */
@WebMvcTest(RequestConnectionController.class)
@Import(SecurityConfiguration.class)
@DisplayName("Request Connection Controller Integration Tests")
class RequestConnectionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendRequestConnectionHandler sendRequestConnectionHandler;

    @MockBean
    private UpdateStatusRequestConnectionHandler updateStatusRequestConnectionHandler;

    @MockBean
    private GetMyRequestConnectionsHandler getMyRequestConnectionsHandler;

    @MockBean
    private AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    @DisplayName("Should return empty list when user has no connection requests")
    void shouldReturnEmptyListWithNoRequests() throws Exception {
        // Arrange
        when(authenticationService.getAuthenticatedPersonId()).thenReturn("user-1");
        when(getMyRequestConnectionsHandler.handle(anyString())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/v1/request-connections/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Should return 401 when getting requests without authentication")
    void shouldReturnUnauthorizedWithoutAuth() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/request-connections/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return 401 when sending request without authentication")
    void shouldReturnUnauthorizedWhenSendingWithoutAuth() throws Exception {
        // Arrange
        SendRequestConnectionRequest request = new SendRequestConnectionRequest(TypeConnection.FRIEND);

        // Act & Assert
        mockMvc.perform(post("/v1/request-connections/some-user-id")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}

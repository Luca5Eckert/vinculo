package com.vinculo.integration.controller;

import com.vinculo.module.post.application.controller.PostController;
import com.vinculo.module.post.application.dto.CreatePostRequest;
import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.application.handler.CreatePostHandler;
import com.vinculo.module.post.application.handler.DeletePostHandler;
import com.vinculo.module.post.application.handler.GetAllByAuthorHandler;
import com.vinculo.module.post.application.handler.GetAllForPersonHandler;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for PostController.
 * Tests post creation, deletion, and retrieval endpoints.
 */
@WebMvcTest(PostController.class)
@Import(SecurityConfiguration.class)
@DisplayName("Post Controller Integration Tests")
class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePostHandler createPostHandler;

    @MockBean
    private DeletePostHandler deletePostHandler;

    @MockBean
    private GetAllForPersonHandler getAllForPersonHandler;

    @MockBean
    private GetAllByAuthorHandler getAllByAuthorHandler;

    @MockBean
    private AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    @DisplayName("Should successfully create a post")
    void shouldCreatePost() throws Exception {
        // Arrange
        CreatePostRequest request = new CreatePostRequest("This is my first post!");
        PostResponse mockResponse = new PostResponse("1", "This is my first post!", LocalDateTime.now(), "user-1");

        when(authenticationService.getAuthenticatedPersonId()).thenReturn("user-1");
        when(createPostHandler.handle(any(CreatePostRequest.class), anyString()))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("This is my first post!"))
                .andExpect(jsonPath("$.authorId").exists())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 400 when creating post with empty content")
    void shouldReturnBadRequestForEmptyContent() throws Exception {
        // Arrange
        CreatePostRequest request = new CreatePostRequest("");

        // Act & Assert
        mockMvc.perform(post("/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 401 when creating post without authentication")
    void shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
        // Arrange
        CreatePostRequest request = new CreatePostRequest("Unauthorized post");

        // Act & Assert
        mockMvc.perform(post("/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Should successfully delete own post")
    void shouldDeleteOwnPost() throws Exception {
        // Arrange
        when(authenticationService.getAuthenticatedPersonId()).thenReturn("user-1");

        // Act & Assert
        mockMvc.perform(delete("/v1/posts/post-123")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("Should get posts for authenticated user")
    void shouldGetPostsForUser() throws Exception {
        // Arrange
        when(authenticationService.getAuthenticatedPersonId()).thenReturn("user-1");
        when(getAllForPersonHandler.handle(anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/v1/posts")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should return 401 when getting posts without authentication")
    void shouldReturnUnauthorizedWhenGettingPostsWithoutAuth() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/posts")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andExpect(status().isUnauthorized());
    }
}

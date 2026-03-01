package com.vinculo.integration;

import com.vinculo.module.post.domain.policy.PostVisibilityPolicy;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration tests for the Post module visibility policy.
 * Tests the post visibility rules and access control.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Post Module Integration Tests")
class PostModuleIntegrationTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @Test
    @DisplayName("Should allow viewing own posts")
    void shouldAllowViewingOwnPosts() {
        // Arrange
        PostVisibilityPolicy policy = new PostVisibilityPolicy(connectionRepository);

        // Act & Assert
        assertTrue(policy.canView("user-123", "user-123"));
        verify(connectionRepository, never()).existsBetween(anyString(), anyString());
    }

    @Test
    @DisplayName("Should allow connected users to view posts")
    void shouldAllowConnectedUsersToViewPosts() {
        // Arrange
        PostVisibilityPolicy policy = new PostVisibilityPolicy(connectionRepository);
        when(connectionRepository.existsBetween("viewer-123", "owner-456")).thenReturn(true);

        // Act
        boolean canView = policy.canView("viewer-123", "owner-456");

        // Assert
        assertTrue(canView);
        verify(connectionRepository).existsBetween("viewer-123", "owner-456");
    }

    @Test
    @DisplayName("Should prevent non-connected users from viewing posts")
    void shouldPreventNonConnectedUsersFromViewingPosts() {
        // Arrange
        PostVisibilityPolicy policy = new PostVisibilityPolicy(connectionRepository);
        when(connectionRepository.existsBetween("viewer-123", "owner-456")).thenReturn(false);

        // Act
        boolean canView = policy.canView("viewer-123", "owner-456");

        // Assert
        assertFalse(canView);
        verify(connectionRepository).existsBetween("viewer-123", "owner-456");
    }
}

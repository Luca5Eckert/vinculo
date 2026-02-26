package com.vinculo.module.post.domain.policy;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostVisibilityPolicyTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private PostVisibilityPolicy postVisibilityPolicy;

    @Test
    @DisplayName("Should return true when viewing own posts")
    void shouldReturnTrueWhenViewingOwnPosts() {
        // Arrange
        String personId = "uuid-1";

        // Act
        boolean result = postVisibilityPolicy.canView(personId, personId);

        // Assert
        assertTrue(result);
        verify(connectionRepository, never()).existsBetween(anyString(), anyString());
    }

    @Test
    @DisplayName("Should return true when connected to post owner")
    void shouldReturnTrueWhenConnectedToPostOwner() {
        // Arrange
        String viewerId = "uuid-1";
        String ownerId = "uuid-2";
        when(connectionRepository.existsBetween(viewerId, ownerId)).thenReturn(true);

        // Act
        boolean result = postVisibilityPolicy.canView(viewerId, ownerId);

        // Assert
        assertTrue(result);
        verify(connectionRepository).existsBetween(viewerId, ownerId);
    }

    @Test
    @DisplayName("Should return false when not connected to post owner")
    void shouldReturnFalseWhenNotConnectedToPostOwner() {
        // Arrange
        String viewerId = "uuid-1";
        String ownerId = "uuid-2";
        when(connectionRepository.existsBetween(viewerId, ownerId)).thenReturn(false);

        // Act
        boolean result = postVisibilityPolicy.canView(viewerId, ownerId);

        // Assert
        assertFalse(result);
        verify(connectionRepository).existsBetween(viewerId, ownerId);
    }
}

package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.post.domain.command.DeletePostCommand;
import com.vinculo.module.post.domain.exception.PostDomainException;
import com.vinculo.module.post.domain.exception.PostNotFoundException;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private DeletePostUseCase deletePostUseCase;

    @Test
    @DisplayName("Should successfully delete post when user is the author")
    void shouldDeletePostWhenUserIsAuthor() {
        // Arrange
        String postId = "post-123";
        String personId = "person-456";
        DeletePostCommand command = new DeletePostCommand(postId, personId);

        Post post = mock(Post.class);
        when(post.getId()).thenReturn(postId);
        when(post.canDelete(personId)).thenReturn(true);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        deletePostUseCase.execute(command);

        // Assert
        verify(postRepository).findById(postId);
        verify(post).canDelete(personId);
        verify(postRepository).deleteById(postId);
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when post does not exist")
    void shouldThrowExceptionWhenPostNotExists() {
        // Arrange
        String postId = "non-existent-post";
        String personId = "person-789";
        DeletePostCommand command = new DeletePostCommand(postId, personId);

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostNotFoundException.class, 
            () -> deletePostUseCase.execute(command));
        
        verify(postRepository).findById(postId);
        verify(postRepository, never()).deleteById(anyString());
    }

    @Test
    @DisplayName("Should throw PostDomainException when user is not the author")
    void shouldThrowExceptionWhenUserIsNotAuthor() {
        // Arrange
        String postId = "post-abc";
        String personId = "person-xyz";
        DeletePostCommand command = new DeletePostCommand(postId, personId);

        Post post = mock(Post.class);
        when(post.canDelete(personId)).thenReturn(false);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act & Assert
        PostDomainException exception = assertThrows(PostDomainException.class, 
            () -> deletePostUseCase.execute(command));
        
        assertTrue(exception.getMessage().contains("not the author"));
        verify(postRepository).findById(postId);
        verify(post).canDelete(personId);
        verify(postRepository, never()).deleteById(anyString());
    }
}

package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.post.domain.command.CreatePostCommand;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CreatePostUseCase createPostUseCase;

    @Test
    @DisplayName("Should successfully create a post for an existing person")
    void shouldCreatePostSuccessfully() {
        // Arrange
        String personId = "person-123";
        String content = "This is my first post!";
        CreatePostCommand command = new CreatePostCommand(content, personId);

        Person person = Person.builder()
            .id(personId)
            .name("John Doe")
            .email("john@example.com")
            .build();

        Post expectedPost = Post.builder()
            .id("post-456")
            .content(content)
            .author(person)
            .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(postRepository.save(any(Post.class))).thenReturn(expectedPost);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

        // Act
        Post result = createPostUseCase.execute(command);

        // Assert
        assertNotNull(result);
        verify(personRepository).findById(personId);
        verify(postRepository).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertEquals(content, savedPost.getContent());
        assertEquals(person, savedPost.getAuthor());
        assertNotNull(savedPost.getCreatedAt());
    }

    @Test
    @DisplayName("Should throw PersonNotExistException when person does not exist")
    void shouldThrowExceptionWhenPersonNotExists() {
        // Arrange
        String personId = "non-existent-id";
        String content = "This post will fail";
        CreatePostCommand command = new CreatePostCommand(content, personId);

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PersonNotExistException.class, 
            () -> createPostUseCase.execute(command));
        
        verify(personRepository).findById(personId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("Should set creation timestamp when creating post")
    void shouldSetCreationTimestamp() {
        // Arrange
        String personId = "person-789";
        String content = "Testing timestamp";
        CreatePostCommand command = new CreatePostCommand(content, personId);

        Person person = Person.builder().id(personId).build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(postRepository.save(any(Post.class))).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

        // Act
        createPostUseCase.execute(command);

        // Assert
        verify(postRepository).save(postCaptor.capture());
        Post savedPost = postCaptor.getValue();
        assertNotNull(savedPost.getCreatedAt());
    }
}

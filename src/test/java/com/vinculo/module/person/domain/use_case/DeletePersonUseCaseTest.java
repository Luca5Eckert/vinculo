package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.command.DeletePersonCommand;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePersonUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private DeletePersonUseCase deletePersonUseCase;

    @Test
    @DisplayName("Should successfully delete an existing person")
    void shouldDeletePersonSuccessfully() {
        // Arrange
        String personId = "person-123";
        DeletePersonCommand command = new DeletePersonCommand(personId);

        when(personRepository.existsById(personId)).thenReturn(true);

        // Act
        deletePersonUseCase.execute(command);

        // Assert
        verify(personRepository).existsById(personId);
        verify(personRepository).deleteById(personId);
    }

    @Test
    @DisplayName("Should throw PersonNotExistException when person does not exist")
    void shouldThrowExceptionWhenPersonNotExists() {
        // Arrange
        String personId = "non-existent-id";
        DeletePersonCommand command = new DeletePersonCommand(personId);

        when(personRepository.existsById(personId)).thenReturn(false);

        // Act & Assert
        assertThrows(PersonNotExistException.class, 
            () -> deletePersonUseCase.execute(command));
        
        verify(personRepository).existsById(personId);
        verify(personRepository, never()).deleteById(anyString());
    }
}

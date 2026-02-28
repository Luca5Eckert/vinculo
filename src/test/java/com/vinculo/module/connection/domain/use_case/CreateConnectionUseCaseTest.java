package com.vinculo.module.connection.domain.use_case;

import com.vinculo.module.connection.domain.command.CreateConnectionCommand;
import com.vinculo.module.connection.domain.exception.ConnectionAlreadyExistsException;
import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.model.TypeConnection;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
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
class CreateConnectionUseCaseTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CreateConnectionUseCase createConnectionUseCase;

    @Test
    @DisplayName("Should successfully create a connection between two people")
    void shouldCreateConnection() {
        // Arrange
        String personId = "person-123";
        String personToConnectId = "person-456";
        CreateConnectionCommand command = new CreateConnectionCommand(
            personId,
            personToConnectId,
            TypeConnection.FRIEND
        );

        Person person = Person.builder()
            .id(personId)
            .name("Alice")
            .build();
        
        Person personToConnect = Person.builder()
            .id(personToConnectId)
            .name("Bob")
            .build();

        when(personRepository.existsConnectionBetween(personId, personToConnectId))
            .thenReturn(false);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.findById(personToConnectId)).thenReturn(Optional.of(personToConnect));

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        // Act
        createConnectionUseCase.execute(command);

        // Assert
        verify(personRepository).existsConnectionBetween(personId, personToConnectId);
        verify(personRepository).save(personCaptor.capture());
        
        Person savedPerson = personCaptor.getValue();
        assertEquals(personId, savedPerson.getId());
    }

    @Test
    @DisplayName("Should throw exception when connection already exists")
    void shouldThrowExceptionWhenConnectionExists() {
        // Arrange
        String personId = "person-789";
        String personToConnectId = "person-101";
        CreateConnectionCommand command = new CreateConnectionCommand(
            personId,
            personToConnectId,
            TypeConnection.COLLEAGUE
        );

        when(personRepository.existsConnectionBetween(personId, personToConnectId))
            .thenReturn(true);

        // Act & Assert
        assertThrows(ConnectionAlreadyExistsException.class,
            () -> createConnectionUseCase.execute(command));
        
        verify(personRepository).existsConnectionBetween(personId, personToConnectId);
        verify(personRepository, never()).findById(anyString());
        verify(personRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when first person does not exist")
    void shouldThrowExceptionWhenFirstPersonNotExists() {
        // Arrange
        String personId = "non-existent-person";
        String personToConnectId = "person-202";
        CreateConnectionCommand command = new CreateConnectionCommand(
            personId,
            personToConnectId,
            TypeConnection.MENTOR
        );

        when(personRepository.existsConnectionBetween(personId, personToConnectId))
            .thenReturn(false);
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PersonNotExistException.class,
            () -> createConnectionUseCase.execute(command));
        
        verify(personRepository).findById(personId);
        verify(personRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when second person does not exist")
    void shouldThrowExceptionWhenSecondPersonNotExists() {
        // Arrange
        String personId = "person-303";
        String personToConnectId = "non-existent-person";
        CreateConnectionCommand command = new CreateConnectionCommand(
            personId,
            personToConnectId,
            TypeConnection.FAMILY
        );

        Person person = Person.builder().id(personId).build();

        when(personRepository.existsConnectionBetween(personId, personToConnectId))
            .thenReturn(false);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.findById(personToConnectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PersonNotExistException.class,
            () -> createConnectionUseCase.execute(command));
        
        verify(personRepository).findById(personToConnectId);
        verify(personRepository, never()).save(any());
    }
}

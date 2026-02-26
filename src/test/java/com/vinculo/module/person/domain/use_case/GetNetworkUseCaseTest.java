package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.command.GetNetworkCommand;
import com.vinculo.module.person.domain.exception.PersonException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetNetworkUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private GetNetworkUseCase getNetworkUseCase;

    @Test
    @DisplayName("Should return connections when user is viewing their own network")
    void shouldReturnNetworkWhenUserIsOwner() {
        // Arrange
        Long personId = 1L;

        Person person = Person.builder().id(personId).name("Alice").build();
        GetNetworkCommand command = new GetNetworkCommand(personId, personId);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        List<Person> result = getNetworkUseCase.execute(command);

        // Assert
        assertNotNull(result);
        verify(personRepository, never()).isConnected(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Should return connections when user is connected with the target")
    void shouldReturnNetworkWhenUserIsConnectedWithTarget() {
        // Arrange
        Long authId = 1L;
        Long targetId = 2L;

        Person friend = Person.builder().id(3L).name("Charlie").build();
        Connection connection = new Connection();
        connection.setPerson(friend);

        Person target = Person.builder().id(targetId).name("Bob").build();
        target.addConnection(connection);

        GetNetworkCommand command = new GetNetworkCommand(authId, targetId);

        when(personRepository.findById(targetId)).thenReturn(Optional.of(target));
        when(personRepository.isConnected(authId, targetId)).thenReturn(true);

        // Act
        List<Person> result = getNetworkUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Charlie", result.get(0).getName());
        verify(personRepository).isConnected(authId, targetId);
    }

    @Test
    @DisplayName("Should throw PersonException when user is not connected to the target")
    void shouldThrowExceptionWhenNotConnected() {
        // Arrange
        Long authId = 1L;
        Long targetId = 2L;
        Person target = Person.builder().id(targetId).name("Bob").build();
        GetNetworkCommand command = new GetNetworkCommand(authId, targetId);

        when(personRepository.findById(targetId)).thenReturn(Optional.of(target));
        when(personRepository.isConnected(authId, targetId)).thenReturn(false);

        // Act & Assert
        assertThrows(PersonException.class, () -> getNetworkUseCase.execute(command));
    }
}


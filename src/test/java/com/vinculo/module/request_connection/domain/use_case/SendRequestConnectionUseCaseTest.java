package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.request_connection.domain.command.SendRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionAlreadyExistsException;
import com.vinculo.module.request_connection.domain.exception.RequesterAndTargetCannotBeTheSameException;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import com.vinculo.module.connection.domain.model.TypeConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
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
class SendRequestConnectionUseCaseTest {

    @Mock
    private RequestConnectionRepository requestConnectionRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private SendRequestConnectionUseCase sendRequestConnectionUseCase;

    @Test
    @DisplayName("Should successfully send connection request")
    void shouldSendConnectionRequest() {
        // Arrange
        String requesterId = "requester-123";
        String targetId = "target-456";
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
            requesterId, 
            targetId, 
            TypeConnection.FRIEND
        );

        Person requester = Person.builder().id(requesterId).build();
        Person target = Person.builder().id(targetId).build();

        when(personRepository.findById(requesterId)).thenReturn(Optional.of(requester));
        when(personRepository.findById(targetId)).thenReturn(Optional.of(target));
        when(requestConnectionRepository.findByAnyRequesterOrTarget(requesterId, targetId))
            .thenReturn(Optional.empty());

        ArgumentCaptor<RequestConnection> captor = ArgumentCaptor.forClass(RequestConnection.class);

        // Act
        sendRequestConnectionUseCase.execute(command);

        // Assert
        verify(requestConnectionRepository).save(captor.capture());
        RequestConnection saved = captor.getValue();
        
        assertEquals(requester, saved.getRequester());
        assertEquals(target, saved.getTarget());
        assertEquals(TypeConnection.FRIEND, saved.getType());
        assertEquals(StatusRequestConnection.PENDING, saved.getStatus());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    @DisplayName("Should throw exception when requester and target are the same")
    void shouldThrowExceptionWhenRequesterAndTargetAreSame() {
        // Arrange
        String personId = "same-person-123";
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
            personId, 
            personId, 
            TypeConnection.FRIEND
        );

        // Act & Assert
        assertThrows(RequesterAndTargetCannotBeTheSameException.class,
            () -> sendRequestConnectionUseCase.execute(command));
        
        verify(personRepository, never()).findById(anyString());
        verify(requestConnectionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when requester does not exist")
    void shouldThrowExceptionWhenRequesterNotExists() {
        // Arrange
        String requesterId = "non-existent-requester";
        String targetId = "target-789";
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
            requesterId, 
            targetId, 
            TypeConnection.COLLEAGUE
        );

        when(personRepository.findById(requesterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PersonNotExistException.class,
            () -> sendRequestConnectionUseCase.execute(command));
        
        verify(personRepository).findById(requesterId);
        verify(requestConnectionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when active request already exists")
    void shouldThrowExceptionWhenActiveRequestExists() {
        // Arrange
        String requesterId = "requester-111";
        String targetId = "target-222";
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
            requesterId, 
            targetId, 
            TypeConnection.MENTOR
        );

        Person requester = Person.builder().id(requesterId).build();
        Person target = Person.builder().id(targetId).build();
        
        RequestConnection existingRequest = RequestConnection.builder()
            .requester(requester)
            .target(target)
            .status(StatusRequestConnection.PENDING)
            .build();

        when(personRepository.findById(requesterId)).thenReturn(Optional.of(requester));
        when(personRepository.findById(targetId)).thenReturn(Optional.of(target));
        when(requestConnectionRepository.findByAnyRequesterOrTarget(requesterId, targetId))
            .thenReturn(Optional.of(existingRequest));

        // Act & Assert
        assertThrows(RequestConnectionAlreadyExistsException.class,
            () -> sendRequestConnectionUseCase.execute(command));
        
        verify(requestConnectionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete rejected request and create new one when target initiates")
    void shouldDeleteRejectedRequestWhenTargetInitiates() {
        // Arrange
        String requesterId = "person-A";
        String targetId = "person-B";
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
            targetId,
            requesterId,
            TypeConnection.FRIEND
        );

        Person personA = Person.builder().id(requesterId).build();
        Person personB = Person.builder().id(targetId).build();
        
        RequestConnection rejectedRequest = RequestConnection.builder()
            .requester(personA)
            .target(personB)
            .status(StatusRequestConnection.REJECTED)
            .build();

        when(personRepository.findById(targetId)).thenReturn(Optional.of(personB));
        when(personRepository.findById(requesterId)).thenReturn(Optional.of(personA));
        when(requestConnectionRepository.findByAnyRequesterOrTarget(targetId, requesterId))
            .thenReturn(Optional.of(rejectedRequest));

        // Act
        sendRequestConnectionUseCase.execute(command);

        // Assert
        verify(requestConnectionRepository).delete(rejectedRequest);
        verify(requestConnectionRepository).save(any(RequestConnection.class));
    }
}

package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.request_connection.domain.command.SendRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionAlreadyExistsException;
import com.vinculo.module.request_connection.domain.exception.RequesterAndTargetCannotBeTheSameException;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class SendRequestConnectionUseCase {

    private final RequestConnectionRepository requestConnectionRepository;
    private final PersonRepository personRepository;

    public SendRequestConnectionUseCase(RequestConnectionRepository requestConnectionRepository, PersonRepository personRepository) {
        this.requestConnectionRepository = requestConnectionRepository;
        this.personRepository = personRepository;
    }

    public void execute(SendRequestConnectionCommand command) {
        if (command.personRequesterId() == command.personTargetId()) {
            throw new RequesterAndTargetCannotBeTheSameException();
        }

        Person personRequester = personRepository.findById(command.personRequesterId())
                .orElseThrow(PersonNotExistException::new);

        Person personTarget = personRepository.findById(command.personTargetId())
                .orElseThrow(PersonNotExistException::new);

        requestConnectionRepository.findByAnyRequesterOrTarget(command.personRequesterId(), command.personTargetId())
                .ifPresent(existingRequest -> handleExistingRequest(existingRequest, command.personRequesterId()));

        RequestConnection requestConnection = RequestConnection.builder()
                .requester(personRequester)
                .target(personTarget)
                .type(command.typeConnection())
                .status(StatusRequestConnection.PENDING)
                .createdAt(java.time.LocalDateTime.now())
                .build();

        requestConnectionRepository.save(requestConnection);
    }

    private void handleExistingRequest(RequestConnection existingRequest, long currentRequesterId) {
        if (existingRequest.getStatus() != StatusRequestConnection.REJECTED) {
            throw new RequestConnectionAlreadyExistsException(
                    "A connection request is already active or accepted between these users."
            );
        }

        if (existingRequest.getRequester().getId() == currentRequesterId) {
            throw new RequestConnectionAlreadyExistsException(
                    "Your previous request was rejected. You cannot send a new one to this user."
            );
        }

        requestConnectionRepository.delete(existingRequest);
    }
}
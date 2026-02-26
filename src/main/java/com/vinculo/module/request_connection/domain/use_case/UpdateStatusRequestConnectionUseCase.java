package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import com.vinculo.module.request_connection.domain.command.UpdateStatusRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionException;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionIsNotForTargetPersonException;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionNotExistException;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusRequestConnectionUseCase {

    private final RequestConnectionRepository repository;
    private final ConnectionRepository connectionRepository;

    public UpdateStatusRequestConnectionUseCase(RequestConnectionRepository repository, ConnectionRepository connectionRepository) {
        this.repository = repository;
        this.connectionRepository = connectionRepository;
    }

    public RequestConnection execute(UpdateStatusRequestConnectionCommand command) {
        RequestConnection request = repository.findById(command.requestConnectionId())
                .orElseThrow(RequestConnectionNotExistException::new);

        if(request.getStatus() == StatusRequestConnection.ACCEPTED){
            throw new RequestConnectionException("Request connection already accepted");
        }
        if (!request.getTarget().getId().equals(command.targetPersonId())) {
            throw new RequestConnectionIsNotForTargetPersonException();
        }

        verifyConnectionBetweenAlreadyExists(request.getRequester().getId(), request.getTarget().getId());

        request.setStatus(command.status());

        return repository.save(request);
    }

    private void verifyConnectionBetweenAlreadyExists(String requesterPersonId, String targetPersonId) {
        if (connectionRepository.existsBetween(requesterPersonId, targetPersonId)) {
            throw new RequestConnectionException("Connection between requester and target person already exists");
        }
    }

}
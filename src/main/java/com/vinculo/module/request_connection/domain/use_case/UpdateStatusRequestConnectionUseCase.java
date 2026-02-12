package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.request_connection.domain.command.UpdateStatusRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionIsNotForTargetPersonException;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionNotExistException;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusRequestConnectionUseCase {

    private final RequestConnectionRepository repository;

    public UpdateStatusRequestConnectionUseCase(RequestConnectionRepository repository) {
        this.repository = repository;
    }

    public RequestConnection execute(UpdateStatusRequestConnectionCommand command) {
        RequestConnection request = repository.findById(command.requestConnectionId())
                .orElseThrow(RequestConnectionNotExistException::new);

        if (request.getTarget().getId() != command.targetPersonId()) {
            throw new RequestConnectionIsNotForTargetPersonException();
        }

        request.setStatus(command.status());

        return repository.save(request);
    }

}
package com.vinculo.module.connection.application.handler;

import com.vinculo.module.connection.application.dto.CreateConnectionRequest;
import com.vinculo.module.connection.domain.command.CreateConnectionCommand;
import com.vinculo.module.connection.domain.use_case.CreateConnectionUseCase;
import org.springframework.stereotype.Component;

@Component
public class CreateConnectionHandler {

    private final CreateConnectionUseCase createConnectionUseCase;

    public CreateConnectionHandler(CreateConnectionUseCase createConnectionUseCase) {
        this.createConnectionUseCase = createConnectionUseCase;
    }

    public void handle(CreateConnectionRequest request, String personId){
        CreateConnectionCommand command = new CreateConnectionCommand(
                personId,
                request.personToConnectId(),
                request.typeConnection()
        );

        createConnectionUseCase.execute(command);
    }

}

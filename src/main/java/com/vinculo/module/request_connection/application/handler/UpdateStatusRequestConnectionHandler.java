package com.vinculo.module.request_connection.application.handler;

import com.vinculo.module.request_connection.application.dto.UpdateStatusRequestConnectionRequest;
import com.vinculo.module.request_connection.domain.command.UpdateStatusRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.service.ConnectionStrategyManager;
import com.vinculo.module.request_connection.domain.use_case.UpdateStatusRequestConnectionUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateStatusRequestConnectionHandler {

    private final UpdateStatusRequestConnectionUseCase updateStatusRequestConnectionUseCase;
    private final ConnectionStrategyManager connectionStrategyManager;

    public UpdateStatusRequestConnectionHandler(UpdateStatusRequestConnectionUseCase updateStatusRequestConnectionUseCase, ConnectionStrategyManager connectionStrategyManager) {
        this.updateStatusRequestConnectionUseCase = updateStatusRequestConnectionUseCase;
        this.connectionStrategyManager = connectionStrategyManager;
    }

    @Transactional
    public void handle(String requestConnectionId, String targetPersonId, UpdateStatusRequestConnectionRequest request) {
        UpdateStatusRequestConnectionCommand command = new UpdateStatusRequestConnectionCommand(
                requestConnectionId, targetPersonId, request.status()
        );

        var requestConnection = updateStatusRequestConnectionUseCase.execute(command);

        connectionStrategyManager.process(requestConnection);
    }

}

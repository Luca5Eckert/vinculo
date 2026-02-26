package com.vinculo.module.request_connection.application.handler;

import com.vinculo.module.request_connection.application.dto.SendRequestConnectionRequest;
import com.vinculo.module.request_connection.domain.command.SendRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.use_case.SendRequestConnectionUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SendRequestConnectionHandler {

    private final SendRequestConnectionUseCase sendRequestConnectionUseCase;

    public SendRequestConnectionHandler(SendRequestConnectionUseCase sendRequestConnectionUseCase) {
        this.sendRequestConnectionUseCase = sendRequestConnectionUseCase;
    }

    @Transactional
    public void handle(String personRequesterId, String personTargetId, SendRequestConnectionRequest request) {
        SendRequestConnectionCommand command = new SendRequestConnectionCommand(
                personRequesterId,
                personTargetId,
                request.typeConnection()
        );

        sendRequestConnectionUseCase.execute(command);
    }

}

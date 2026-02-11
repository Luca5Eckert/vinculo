package com.vinculo.module.connection.application.handler;

import com.vinculo.module.connection.application.dto.ConnectionResponse;
import com.vinculo.module.connection.application.mapper.ConnectionMapper;
import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.use_case.GetMyConnectionsUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GetMyConnectionsHandler {

    private final GetMyConnectionsUseCase getMyConnectionsUseCase;

    private final ConnectionMapper connectionMapper;

    public GetMyConnectionsHandler(GetMyConnectionsUseCase getMyConnectionsUseCase, ConnectionMapper connectionMapper) {
        this.getMyConnectionsUseCase = getMyConnectionsUseCase;
        this.connectionMapper = connectionMapper;
    }

    @Transactional(readOnly = true)
    public List<ConnectionResponse> handle(long userId) {
        List<Connection> connections = getMyConnectionsUseCase.execute(userId);

        return connections
                .stream()
                .map(connectionMapper::toResponse)
                .toList();
    }

}

package com.vinculo.module.request_connection.application.handler;

import com.vinculo.module.request_connection.application.dto.RequestConnectionResponse;
import com.vinculo.module.request_connection.application.mapper.RequestConnectionMapper;
import com.vinculo.module.request_connection.domain.use_case.GetMyRequestConnectionsUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GetMyRequestConnectionsHandler {

    private final GetMyRequestConnectionsUseCase getMyRequestConnectionsUseCase;

    private final RequestConnectionMapper requestConnectionMapper;

    public GetMyRequestConnectionsHandler(GetMyRequestConnectionsUseCase getMyRequestConnectionsUseCase, RequestConnectionMapper requestConnectionMapper) {
        this.getMyRequestConnectionsUseCase = getMyRequestConnectionsUseCase;
        this.requestConnectionMapper = requestConnectionMapper;
    }

    @Transactional(readOnly = true)
    public List<RequestConnectionResponse> handle(String personId){
        var requestConnections = getMyRequestConnectionsUseCase.execute(personId);

        return requestConnections.stream()
                .map(requestConnectionMapper::toResponse)
                .toList();
    }

}

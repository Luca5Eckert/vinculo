package com.vinculo.module.request_connection.application.mapper;

import com.vinculo.module.request_connection.application.dto.RequestConnectionResponse;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import org.springframework.stereotype.Component;

@Component
public class RequestConnectionMapper {
    public RequestConnectionResponse toResponse(RequestConnection requestConnection) {
        return new RequestConnectionResponse(
                requestConnection.getId(),
                requestConnection.getRequester().getId(),
                requestConnection.getTarget().getId(),
                requestConnection.getStatus()
        );
    }
}

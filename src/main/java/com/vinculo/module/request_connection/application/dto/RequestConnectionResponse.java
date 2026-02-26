package com.vinculo.module.request_connection.application.dto;

import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;

public record RequestConnectionResponse(
        String id,
        String personRequesterId,
        String personTargetId,
        StatusRequestConnection status
) {
}

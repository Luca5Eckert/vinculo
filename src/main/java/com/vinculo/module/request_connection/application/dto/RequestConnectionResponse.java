package com.vinculo.module.request_connection.application.dto;

import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;

public record RequestConnectionResponse(
        long id,
        long personRequesterId,
        long personTargetId,
        StatusRequestConnection status
) {
}

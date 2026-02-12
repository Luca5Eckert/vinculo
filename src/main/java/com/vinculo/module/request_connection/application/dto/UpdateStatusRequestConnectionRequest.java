package com.vinculo.module.request_connection.application.dto;

import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;

public record UpdateStatusRequestConnectionRequest(
        StatusRequestConnection status
) {
}

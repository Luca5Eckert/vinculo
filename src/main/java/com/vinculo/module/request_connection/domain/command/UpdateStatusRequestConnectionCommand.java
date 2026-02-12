package com.vinculo.module.request_connection.domain.command;

import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;

public record UpdateStatusRequestConnectionCommand (
        long requestConnectionId,
        long targetPersonId,
        StatusRequestConnection status
) {
}

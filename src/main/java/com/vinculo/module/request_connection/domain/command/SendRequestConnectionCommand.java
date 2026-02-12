package com.vinculo.module.request_connection.domain.command;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record SendRequestConnectionCommand(
        long personRequesterId,
        long personTargetId,
        TypeConnection typeConnection
) {
}

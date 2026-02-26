package com.vinculo.module.request_connection.domain.command;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record SendRequestConnectionCommand(
        String personRequesterId,
        String personTargetId,
        TypeConnection typeConnection
) {
}

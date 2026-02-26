package com.vinculo.module.connection.domain.command;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record CreateConnectionCommand(
        String personId,
        String personToConnectId,
        TypeConnection typeConnection
) {
}

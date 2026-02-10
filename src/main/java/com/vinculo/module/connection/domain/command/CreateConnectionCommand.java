package com.vinculo.module.connection.domain.command;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record CreateConnectionCommand(
        long personId,
        long connectPersonId,
        TypeConnection typeConnection
) {
}

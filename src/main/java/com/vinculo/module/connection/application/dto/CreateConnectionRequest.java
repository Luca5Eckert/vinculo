package com.vinculo.module.connection.application.dto;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record CreateConnectionRequest(
        String personToConnectId,
        TypeConnection typeConnection
) {
}

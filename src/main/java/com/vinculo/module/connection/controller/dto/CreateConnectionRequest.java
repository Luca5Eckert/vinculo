package com.vinculo.module.connection.controller.dto;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record CreateConnectionRequest(
        long personToConnectId,
        TypeConnection typeConnection
) {
}

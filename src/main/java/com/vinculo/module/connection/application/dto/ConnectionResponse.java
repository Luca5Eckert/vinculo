package com.vinculo.module.connection.application.dto;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record ConnectionResponse(
        PersonResponseConnection personId,
        TypeConnection typeConnection
) {
}

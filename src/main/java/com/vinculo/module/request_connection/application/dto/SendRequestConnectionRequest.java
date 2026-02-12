package com.vinculo.module.request_connection.application.dto;

import com.vinculo.module.connection.domain.model.TypeConnection;

public record SendRequestConnectionRequest(
        TypeConnection typeConnection
) {
}

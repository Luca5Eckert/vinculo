package com.vinculo.module.connection.domain.port;

import com.vinculo.module.connection.domain.model.Connection;

import java.util.List;

public interface ConnectionRepository {
    List<Connection> findAllByPersonId(long personId);

    boolean existsBetween(long requesterPersonId, long targetPersonId);
}

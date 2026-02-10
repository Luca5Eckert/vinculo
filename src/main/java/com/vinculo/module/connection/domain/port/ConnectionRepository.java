package com.vinculo.module.connection.domain.port;

public interface ConnectionRepository {
    boolean existsByPersonIdAndConnectedPersonId(long userId, long connectedUserId);
}

package com.vinculo.module.connection.domain.port;

public interface ConnectionRepository {
    boolean existsByUserIdAndConnectedUserId(long userId, long connectedUserId);
}

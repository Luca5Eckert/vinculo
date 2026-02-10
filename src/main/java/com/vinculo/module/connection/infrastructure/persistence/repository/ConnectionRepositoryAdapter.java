package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionRepositoryAdapter implements ConnectionRepository {

    private final ConnectionRepositoryNeo4j connectionRepositoryNeo4j;

    public ConnectionRepositoryAdapter(ConnectionRepositoryNeo4j connectionRepositoryNeo4j) {
        this.connectionRepositoryNeo4j = connectionRepositoryNeo4j;
    }

    @Override
    public boolean existsByPersonIdAndConnectedPersonId(long userId, long connectedUserId) {
        return connectionRepositoryNeo4j.existsByUserIdAndConnectedUserId(userId, connectedUserId);
    }
}

package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectionRepositoryAdapter implements ConnectionRepository {

    private final ConnectionRepositoryNeo4j connectionRepositoryNeo4j;

    public ConnectionRepositoryAdapter(ConnectionRepositoryNeo4j connectionRepositoryNeo4j) {
        this.connectionRepositoryNeo4j = connectionRepositoryNeo4j;
    }


    @Override
    public List<Connection> findAllByPersonId(long personId) {
        return connectionRepositoryNeo4j.findAllByPersonId(personId);
    }
}

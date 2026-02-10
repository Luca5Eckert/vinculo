package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepositoryNeo4j extends Neo4jRepository<Connection, Long> {

    boolean existsByUserIdAndConnectedUserId(Long userId, Long connectedUserId);
}
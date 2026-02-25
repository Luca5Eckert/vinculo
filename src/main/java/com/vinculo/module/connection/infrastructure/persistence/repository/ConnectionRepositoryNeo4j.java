package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepositoryNeo4j extends Neo4jRepository<Connection, Long> {

    @Query("""
            MATCH (p1:person)-[r:CONNECTED_WITH]->(p2:person)
            WHERE id(p1) = $personId
            RETURN r, p1, p2
            """)
    List<Connection> findAllByPersonId(Long personId);

    @Query("""
            MATCH (p1:person)-[:CONNECTED_WITH]-(p2:person)
            WHERE id(p1) = $requesterPersonId AND id(p2) = $targetPersonId
            RETURN COUNT(p1) > 0
            """)
    boolean existsBetween(Long requesterPersonId, Long targetPersonId);
}
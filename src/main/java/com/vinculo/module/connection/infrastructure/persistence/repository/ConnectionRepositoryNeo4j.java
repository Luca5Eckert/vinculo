package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepositoryNeo4j extends Neo4jRepository<Connection, String> {

    @Query("""
            MATCH (p1:Person {id: $personId})-[r:CONNECTED_WITH]->(p2:Person)
            RETURN r, p1, p2
            """)
    List<Connection> findAllByPersonId(String personId);

    @Query("""
            MATCH (p1:Person {id: $requesterPersonId})-[:CONNECTED_WITH]-(p2:Person {id: $targetPersonId})
            RETURN COUNT(p1) > 0
            """)
    boolean existsBetween(String requesterPersonId, String targetPersonId);
}
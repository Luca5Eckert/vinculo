package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepositoryNeo4j extends Neo4jRepository<Connection, Long> {

    @Query("MATCH (p1:Person)-[r:CONNECTED_WITH]-(p2:Person) " +
            "WHERE id(p1) = $authorId " +
            "RETURN r, collect(p1), collect(p2)")
    List<Connection> findAllByPersonId(long personId);

    @Query("MATCH (p1:Person)-[:CONNECTED_WITH]-(p2:Person) " +
            "WHERE id(p1) = $requesterPersonId AND id(p2) = $targetPersonId " +
            "RETURN count(p1) > 0")
    boolean existsBetween(long requesterPersonId, long targetPersonId);
}
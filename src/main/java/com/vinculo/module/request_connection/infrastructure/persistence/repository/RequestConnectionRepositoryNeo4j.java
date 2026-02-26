package com.vinculo.module.request_connection.infrastructure.persistence.repository;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestConnectionRepositoryNeo4j extends Neo4jRepository<RequestConnection, String> {

    @Query("""
        MATCH (r:RequestConnection)-[:REQUESTED_BY]->(requester:Person)
        MATCH (r)-[:SENT_TO]->(target:Person)
        WHERE elementId(requester) = $requesterId AND elementId(target) = $targetId
        RETURN count(r) > 0
        """)
    boolean existsByRequesterIdAndTargetId(String requesterId, String targetId);

    @Query("""
        MATCH (r:RequestConnection)-[f:REQUESTED_BY]->(requester:Person)
        MATCH (r)-[t:SENT_TO]->(target:Person)
        WHERE elementId(target) = $targetId
        RETURN r, f, t, requester, target
        """)
    List<RequestConnection> findAllByTargetId(String targetId);

    @Query("""
            MATCH (r:RequestConnection)-[f:REQUESTED_BY]->(p1:Person)
            MATCH (r)-[t:SENT_TO]->(p2:Person)
            WHERE (elementId(p1) = $requesterId AND elementId(p2) = $targetId)
               OR (elementId(p1) = $targetId AND elementId(p2) = $requesterId)
            RETURN r, f, t, p1, p2
            LIMIT 1
            """)
    Optional<RequestConnection> findByAnyRequesterOrTarget(String requesterId, String targetId);
}
package com.vinculo.module.request_connection.infrastructure.persistence.repository;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestConnectionRepositoryNeo4j extends Neo4jRepository<RequestConnection, Long> {
    boolean existsByRequesterIdAndTargetId(Long requesterId, Long targetId);

    List<RequestConnection> findAllByTargetId(Long targetId);

    @Query("""
            MATCH (p1:Person)-[f:FROM]->(r:RequestConnection)-[t:TO]->(p2:Person)
            
            WHERE (p1.id = $requesterId AND p2.id = $targetId)
               OR (p1.id = $targetId AND p2.id = $requesterId)
            
            RETURN r, f, t, p1, p2
            LIMIT 1
            """)
    Optional<RequestConnection> findByAnyRequesterOrTarget(Long requesterId, Long targetId);
}
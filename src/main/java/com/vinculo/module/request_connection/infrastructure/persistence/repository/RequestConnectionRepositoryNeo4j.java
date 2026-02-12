package com.vinculo.module.request_connection.infrastructure.persistence.repository;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestConnectionRepositoryNeo4j extends Neo4jRepository<RequestConnection, Long> {

    boolean existsByRequesterIdAndTargetId(Long requesterId, Long targetId);

    List<RequestConnection> findAllByTargetId(Long targetId);

}

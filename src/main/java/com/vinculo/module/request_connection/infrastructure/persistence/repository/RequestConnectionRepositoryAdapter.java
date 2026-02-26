package com.vinculo.module.request_connection.infrastructure.persistence.repository;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RequestConnectionRepositoryAdapter implements RequestConnectionRepository {

    private final RequestConnectionRepositoryNeo4j requestConnectionRepositoryNeo4j;

    public RequestConnectionRepositoryAdapter(RequestConnectionRepositoryNeo4j requestConnectionRepositoryNeo4j) {
        this.requestConnectionRepositoryNeo4j = requestConnectionRepositoryNeo4j;
    }

    @Override
    public boolean existsBySenderIdAndReceiverId(String personSenderId, String personReceiverId) {
        return requestConnectionRepositoryNeo4j.existsByRequesterIdAndTargetId(personSenderId, personReceiverId);
    }

    @Override
    public RequestConnection save(RequestConnection requestConnection) {
        return requestConnectionRepositoryNeo4j.save(requestConnection);
    }

    @Override
    public Optional<RequestConnection> findById(String id) {
        return requestConnectionRepositoryNeo4j.findById(id);
    }

    @Override
    public List<RequestConnection> findAllByTargetId(String personId) {
        return requestConnectionRepositoryNeo4j.findAllByTargetId(personId);
    }

    @Override
    public Optional<RequestConnection> findByAnyRequesterOrTarget(String requesterId, String targetId) {
        return requestConnectionRepositoryNeo4j.findByAnyRequesterOrTarget(requesterId, targetId);
    }

    @Override
    public void delete(RequestConnection existingRequest) {
        requestConnectionRepositoryNeo4j.delete(existingRequest);
    }
}

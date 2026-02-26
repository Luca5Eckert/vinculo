package com.vinculo.module.request_connection.domain.port;

import com.vinculo.module.request_connection.domain.model.RequestConnection;

import java.util.List;
import java.util.Optional;

public interface RequestConnectionRepository {
    boolean existsBySenderIdAndReceiverId(String personSenderId, String personReceiverId);

    RequestConnection save(RequestConnection requestConnection);

    Optional<RequestConnection> findById(String id);

    List<RequestConnection> findAllByTargetId(String personId);

    Optional<RequestConnection> findByAnyRequesterOrTarget(String requesterId, String targetId);

    void delete(RequestConnection existingRequest);
}

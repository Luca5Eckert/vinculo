package com.vinculo.module.request_connection.domain.port;

import com.vinculo.module.request_connection.domain.model.RequestConnection;

import java.util.List;
import java.util.Optional;

public interface RequestConnectionRepository {
    boolean existsBySenderIdAndReceiverId(long personSenderId, long personReceiverId);

    RequestConnection save(RequestConnection requestConnection);

    Optional<RequestConnection> findById(long id);

    List<RequestConnection> findAllByTargetId(long personId);

    Optional<RequestConnection> findByAnyRequesterOrTarget(long requesterId, long targetId);

    void delete(RequestConnection existingRequest);
}

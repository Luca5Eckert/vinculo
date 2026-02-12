package com.vinculo.module.request_connection.domain.port;

import com.vinculo.module.request_connection.domain.model.RequestConnection;

import java.util.Optional;

public interface RequestConnectionRepository {
    boolean existsBySenderIdAndReceiverId(long personSenderId, long personReceiverId);

    RequestConnection save(RequestConnection requestConnection);

    Optional<RequestConnection> findById(long id);
}

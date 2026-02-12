package com.vinculo.module.request_connection.domain.port;

import com.vinculo.module.request_connection.domain.model.RequestConnection;

public interface RequestConnectionRepository {
    boolean existsBySenderIdAndReceiverId(long personSenderId, long personReceiverId);

    void save(RequestConnection requestConnection);
}

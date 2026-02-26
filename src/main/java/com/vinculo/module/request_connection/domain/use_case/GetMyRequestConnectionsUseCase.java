package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMyRequestConnectionsUseCase {

    private final RequestConnectionRepository requestConnectionRepository;

    public GetMyRequestConnectionsUseCase(RequestConnectionRepository requestConnectionRepository) {
        this.requestConnectionRepository = requestConnectionRepository;
    }

    public List<RequestConnection> execute(String personId){
        return requestConnectionRepository.findAllByTargetId(personId);
    }


}

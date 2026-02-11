package com.vinculo.module.connection.domain.use_case;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMyConnectionsUseCase {

    private final ConnectionRepository connectionRepository;

    public GetMyConnectionsUseCase(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<Connection> execute(long personId){
        return connectionRepository.findAllByPersonId(personId);
    }

}

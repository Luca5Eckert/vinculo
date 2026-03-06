package com.vinculo.module.graph.domain.policy;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class GraphVisibilityPolicy {

    private final ConnectionRepository connectionRepository;

    public GraphVisibilityPolicy(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public boolean canView(String viewerId, String personId) {
        if(viewerId.equals(personId)) return true;

        return connectionRepository.existsBetween(viewerId, personId);
    }


}

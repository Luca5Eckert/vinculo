package com.vinculo.module.post.domain.policy;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class PostVisibilityPolicy {

    private final ConnectionRepository connectionRepository;

    public PostVisibilityPolicy(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public boolean canView(long authenticatedPerson, long postOwner) {
        if (authenticatedPerson == postOwner) {
            return true;
        }

        return connectionRepository.existsBetween(authenticatedPerson, postOwner);
    }

}

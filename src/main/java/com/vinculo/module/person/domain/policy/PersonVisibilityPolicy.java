package com.vinculo.module.person.domain.policy;

import com.vinculo.module.connection.domain.port.ConnectionRepository;
import com.vinculo.module.person.domain.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonVisibilityPolicy {

    private final ConnectionRepository connectionRepository;

    public PersonVisibilityPolicy(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public boolean haveFullAccess(long viewerId, long personId) {
        if(viewerId == personId) return true;

        return connectionRepository.existsBetween(viewerId, personId);
    }

}

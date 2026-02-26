package com.vinculo.module.connection.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import com.vinculo.module.person.infrastructure.persistence.repository.PersonRepositoryNeo4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectionRepositoryAdapter implements ConnectionRepository {

    private final PersonRepositoryNeo4j personRepositoryNeo4j;

    public ConnectionRepositoryAdapter(PersonRepositoryNeo4j personRepositoryNeo4j) {
        this.personRepositoryNeo4j = personRepositoryNeo4j;
    }


    @Override
    public List<Connection> findAllByPersonId(String personId) {
        return personRepositoryNeo4j.findAllConnectionsByPersonId(personId);
    }

    @Override
    public boolean existsBetween(String requesterPersonId, String targetPersonId) {
        return personRepositoryNeo4j.existsConnectionBetween(requesterPersonId, targetPersonId);
    }


}

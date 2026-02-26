package com.vinculo.module.connection.domain.use_case;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GetMyConnectionsUseCase {

    private final PersonRepository personRepository;

    public GetMyConnectionsUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Set<Connection> execute(String personId){
        Person person = personRepository.findById(personId)
                .orElseThrow(PersonNotExistException::new);

        return person.getConnections();
    }

}

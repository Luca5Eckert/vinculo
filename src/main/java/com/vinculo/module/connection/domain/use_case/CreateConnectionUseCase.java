package com.vinculo.module.connection.domain.use_case;

import com.vinculo.module.connection.domain.command.CreateConnectionCommand;
import com.vinculo.module.connection.domain.exception.ConnectionAlreadyExistsException;
import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.connection.domain.port.ConnectionRepository;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateConnectionUseCase {

    private final ConnectionRepository connectionRepository;
    private final PersonRepository personRepository;

    public CreateConnectionUseCase(ConnectionRepository connectionRepository, PersonRepository personRepository) {
        this.connectionRepository = connectionRepository;
        this.personRepository = personRepository;
    }

    public void execute(CreateConnectionCommand command) {
        if (connectionRepository.existsByUserIdAndConnectedUserId(command.personId(), command.connectPersonId())) {
            throw new ConnectionAlreadyExistsException();
        }

        Person person = personRepository.findById(command.personId())
                .orElseThrow(PersonNotExistException::new);

        Person personToConnect = personRepository.findById(command.connectPersonId())
                .orElseThrow(PersonNotExistException::new);

        Connection connection = Connection.builder()
                .person(personToConnect)
                .type(command.typeConnection())
                .build();

        person.addConnection(connection);

    }


}

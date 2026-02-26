package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.command.GetNetworkCommand;
import com.vinculo.module.person.domain.exception.PersonException;
import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetNetworkUseCase {

    private final PersonRepository personRepository;

    public GetNetworkUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> execute(GetNetworkCommand command) {
        Person networkPerson = personRepository.findById(command.personId())
                .orElseThrow(PersonNotExistException::new);

        verifyIfCanSeeNetwork(command.authenticatedPersonId(), networkPerson);

        return networkPerson.getConnections().stream()
                .map(Connection::getPerson)
                .toList();

    }

    private void verifyIfCanSeeNetwork(Long authenticatedPersonId, Person networkPerson) {
        if(authenticatedPersonId.equals(networkPerson.getId())) return;

        boolean isConnectedWith = networkPerson.getConnections().stream()
                .anyMatch(connection -> isConnected(connection.getPerson(),authenticatedPersonId));

        if(!isConnectedWith){
            throw new PersonException("You don't have permission to see this person's network");
        }
    }

    private boolean isConnected(Person person, Long authenticatedPersonId) {
        return person.getId().equals(authenticatedPersonId);
    }

}

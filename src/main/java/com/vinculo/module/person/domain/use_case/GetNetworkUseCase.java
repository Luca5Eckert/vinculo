package com.vinculo.module.person.domain.use_case;

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
        Person targetPerson = personRepository.findById(command.personId())
                .orElseThrow(PersonNotExistException::new);

        validateAccess(command.authenticatedPersonId(), targetPerson);

        return targetPerson.getConnectedPeople();
    }

    private void validateAccess(Long authId, Person target) {
        boolean isOwner = authId.equals(target.getId());

        if (!isOwner && !personRepository.isConnected(authId, target.getId())) {
            throw new PersonException("You don't have access to this person's network");
        }
    }

}
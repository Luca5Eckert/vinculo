package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class GetPersonUseCase {

    private final PersonRepository personRepository;

    public GetPersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person execute(String personId) {
        return personRepository.findById(personId)
                .orElseThrow(PersonNotExistException::new);
    }

}
package com.vinculo.module.person.domain.use_case;

import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPersonUseCase {

    private final PersonRepository personRepository;

    public GetAllPersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> execute(
            int page,
            int size
    ) {
        return personRepository.findAll(
                page,
                size
        );
    }
}

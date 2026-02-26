package com.vinculo.module.person.controller.mapper;

import com.vinculo.module.person.controller.dto.GetAllPersonResponse;
import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.domain.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public GetAllPersonResponse of(Person person) {
        return new GetAllPersonResponse(
                person.getId(),
                person.getName()
        );
    }

    public PersonResponse toPersonResponse(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName()
        );
    }
}

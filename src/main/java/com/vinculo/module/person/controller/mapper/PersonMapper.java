package com.vinculo.module.person.controller.mapper;

import com.vinculo.module.person.controller.dto.GetAllPersonResponse;
import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.domain.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public GetAllPersonResponse toGetAllResponse(Person person) {
        return new GetAllPersonResponse(
                person.getId(),
                person.getName(),
                person.getUsername()
        );
    }

    public PersonResponse toResponse(Person person, boolean fullAccess) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getUsername(),
                fullAccess ? person.getEmail() : null,
                fullAccess ? person.getPhoneNumber() : null,
                fullAccess
        );
    }

}

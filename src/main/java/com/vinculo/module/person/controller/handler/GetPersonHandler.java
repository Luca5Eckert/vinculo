package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.use_case.GetPersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetPersonHandler {

    private final GetPersonUseCase getPersonUseCase;

    public GetPersonHandler(GetPersonUseCase getPersonUseCase) {
        this.getPersonUseCase = getPersonUseCase;
    }

    @Transactional(readOnly = true)
    public PersonResponse handle(Long personId) {
        Person person = getPersonUseCase.execute(personId);

        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getPhoneNumber()
        );
    }

}
package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.mapper.PersonMapper;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.policy.PersonVisibilityPolicy;
import com.vinculo.module.person.domain.use_case.GetPersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetPersonHandler {

    private final GetPersonUseCase getPersonUseCase;
    private final PersonVisibilityPolicy personVisibilityPolicy;
    private final PersonMapper personMapper;

    public GetPersonHandler(GetPersonUseCase getPersonUseCase, PersonVisibilityPolicy personVisibilityPolicy, PersonMapper personMapper) {
        this.getPersonUseCase = getPersonUseCase;
        this.personVisibilityPolicy = personVisibilityPolicy;
        this.personMapper = personMapper;
    }

    @Transactional(readOnly = true)
    public PersonResponse handle(long authenticatedPerson, Long personId) {
        Person person = getPersonUseCase.execute(personId);

        boolean fullAccess = personVisibilityPolicy.haveFullAccess(authenticatedPerson, person.getId());

        return personMapper.toResponse(person, fullAccess);
    }

}
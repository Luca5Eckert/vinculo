package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.GetAllPersonResponse;
import com.vinculo.module.person.controller.mapper.PersonMapper;
import com.vinculo.module.person.domain.use_case.GetAllPersonUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPersonHandler {

    private final GetAllPersonUseCase getAllPersonUseCase;

    private final PersonMapper personMapper;

    public GetAllPersonHandler(GetAllPersonUseCase getAllPersonUseCase, PersonMapper personMapper) {
        this.getAllPersonUseCase = getAllPersonUseCase;
        this.personMapper = personMapper;
    }

    public List<GetAllPersonResponse> handle(
            int page,
            int size
    ) {
        var persons = getAllPersonUseCase.execute(
                page,
                size
        );

        return persons.stream()
                .map(personMapper::of)
                .toList();
    }


}

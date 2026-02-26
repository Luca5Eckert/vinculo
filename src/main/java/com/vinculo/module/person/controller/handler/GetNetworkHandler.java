package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.mapper.PersonMapper;
import com.vinculo.module.person.domain.command.GetNetworkCommand;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.use_case.GetNetworkUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetNetworkHandler {

    private final GetNetworkUseCase getNetworkUseCase;
    private final PersonMapper personMapper;

    public GetNetworkHandler(GetNetworkUseCase getNetworkUseCase, PersonMapper personMapper) {
        this.getNetworkUseCase = getNetworkUseCase;
        this.personMapper = personMapper;
    }

    public List<PersonResponse> handle(Long personId) {
        var command = GetNetworkCommand.of(personId);

        var network = getNetworkUseCase.execute(command);

        return network.stream()
                .map(person -> personMapper.toResponse(person, true))
                .toList();
    }

}

package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.PersonResponse;
import com.vinculo.module.person.controller.mapper.PersonMapper;
import com.vinculo.module.person.domain.command.GetNetworkCommand;
import com.vinculo.module.person.domain.use_case.GetNetworkUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMyNetworkHandler {

    private final GetNetworkUseCase getNetworkUseCase;
    private final PersonMapper personMapper;

    public GetMyNetworkHandler(GetNetworkUseCase getNetworkUseCase, PersonMapper personMapper) {
        this.getNetworkUseCase = getNetworkUseCase;
        this.personMapper = personMapper;
    }

    public List<PersonResponse> handle(String personId) {
        var command = GetNetworkCommand.of(personId);

        var network = getNetworkUseCase.execute(command);

        return network.stream()
                .map(person -> personMapper.toResponse(person, true))
                .toList();
    }

}

package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.CreatePersonRequest;
import com.vinculo.module.person.domain.command.CreatePersonCommand;
import com.vinculo.module.person.domain.use_case.CreatePersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePersonHandler {

    private final CreatePersonUseCase createPersonUseCase;

    public CreatePersonHandler(CreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
    }

    @Transactional
    public void handle(CreatePersonRequest request){
        CreatePersonCommand command = new CreatePersonCommand(
                request.name(),
                request.phoneNumber(),
                request.email(),
                request.password()
        );

        createPersonUseCase.execute(command);
    }

}

package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.controller.dto.UpdatePersonRequest;
import com.vinculo.module.person.domain.command.UpdatePersonCommand;
import com.vinculo.module.person.domain.use_case.UpdatePersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdatePersonHandler {

    private final UpdatePersonUseCase updatePersonUseCase;

    public UpdatePersonHandler(UpdatePersonUseCase updatePersonUseCase) {
        this.updatePersonUseCase = updatePersonUseCase;
    }

    @Transactional
    public void handle(Long personId, UpdatePersonRequest request) {
        UpdatePersonCommand command = new UpdatePersonCommand(
                personId,
                request.name(),
                request.phoneNumber()
        );

        updatePersonUseCase.execute(command);
    }

}

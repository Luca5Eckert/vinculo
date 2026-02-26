package com.vinculo.module.person.controller.handler;

import com.vinculo.module.person.domain.command.DeletePersonCommand;
import com.vinculo.module.person.domain.use_case.DeletePersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePersonHandler {

    private final DeletePersonUseCase deletePersonUseCase;

    public DeletePersonHandler(DeletePersonUseCase deletePersonUseCase) {
        this.deletePersonUseCase = deletePersonUseCase;
    }

    @Transactional
    public void handle(String personId){
        DeletePersonCommand command = new DeletePersonCommand(
                personId
        );

        deletePersonUseCase.execute(command);
    }

}

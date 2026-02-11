package com.vinculo.module.auth.application.handler;

import com.vinculo.module.auth.application.dto.RegisterPersonRequest;
import com.vinculo.module.auth.domain.command.RegisterPersonCommand;
import com.vinculo.module.auth.domain.use_case.RegisterPersonUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegisterPersonHandler {

    private final RegisterPersonUseCase registerPersonUseCase;

    public RegisterPersonHandler(RegisterPersonUseCase registerPersonUseCase) {
        this.registerPersonUseCase = registerPersonUseCase;
    }

    @Transactional
    public void handle(RegisterPersonRequest request){
        RegisterPersonCommand command = new RegisterPersonCommand(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.password()
        );

        registerPersonUseCase.execute(command);
    }

}

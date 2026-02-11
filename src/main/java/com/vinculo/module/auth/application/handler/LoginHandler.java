package com.vinculo.module.auth.application.handler;

import com.vinculo.module.auth.domain.command.LoginCommand;
import com.vinculo.module.auth.domain.use_case.LoginUseCase;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler {

    private final LoginUseCase loginUseCase;

    public LoginHandler(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public String handle(LoginRequest request) {
        LoginCommand command = new LoginCommand(
                request.username(),
                request.password()
        );

        return loginUseCase.execute(command);
    }

    public record LoginRequest(String username, String password) {
    }

}

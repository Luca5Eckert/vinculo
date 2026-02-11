package com.vinculo.module.auth.domain.use_case;

import com.vinculo.module.auth.domain.command.LoginCommand;
import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.auth.domain.port.AuthenticatorPort;
import com.vinculo.module.auth.domain.port.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {

    private final AuthenticatorPort authenticatorPort;
    private final TokenProvider tokenProvider;

    public LoginUseCase(AuthenticatorPort authenticatorPort, TokenProvider tokenProvider) {
        this.authenticatorPort = authenticatorPort;
        this.tokenProvider = tokenProvider;
    }

    public String execute(LoginCommand command) {
        AuthenticatedUser user = authenticatorPort.authenticate(
                command.email(),
                command.password()
        );

        return tokenProvider.createToken(
                user.email(),
                user.id(),
                user.roles()
        );
    }
}

package com.vinculo.module.auth.infrastructure.security.authenticator;

import com.vinculo.module.auth.domain.model.AuthenticatedUser;
import com.vinculo.module.auth.domain.port.AuthenticatorPort;
import com.vinculo.module.auth.infrastructure.security.user.UserDetailsAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuthenticatorAdapter implements AuthenticatorPort {

    private final AuthenticationManager authenticationManager;

    public SpringSecurityAuthenticatorAdapter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticatedUser authenticate(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );


        UserDetailsAdapter principal = (UserDetailsAdapter) auth.getPrincipal();

        return new AuthenticatedUser(
                principal.id(),
                principal.getUsername(),
                principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }
}
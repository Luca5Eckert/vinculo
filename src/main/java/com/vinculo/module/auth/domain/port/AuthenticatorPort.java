package com.vinculo.module.auth.domain.port;

import com.vinculo.module.auth.domain.model.AuthenticatedUser;

public interface AuthenticatorPort {
    AuthenticatedUser authenticate(String email, String password);
}
package com.vinculo.module.auth.domain.port;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface TokenProvider {

    public String createToken(String email, long userId, List<String> roles);

}

package com.vinculo.module.auth.domain.port;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface TokenProvider {

    public String createToken(String email, long userId, Collection<? extends GrantedAuthority> grantedAuthorities);

}

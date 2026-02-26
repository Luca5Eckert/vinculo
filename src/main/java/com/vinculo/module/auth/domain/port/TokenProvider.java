package com.vinculo.module.auth.domain.port;

import java.util.List;

public interface TokenProvider {

    public String createToken(String email, String userId, List<String> roles);

}

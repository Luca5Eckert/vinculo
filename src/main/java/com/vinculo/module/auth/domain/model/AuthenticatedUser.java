package com.vinculo.module.auth.domain.model;

import java.util.List;

public record AuthenticatedUser(
        long id,
        String email,
        List<String> roles
) {}
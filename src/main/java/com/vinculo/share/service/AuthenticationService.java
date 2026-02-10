package com.vinculo.share.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public long getAuthenticatedPersonId() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof Jwt jwt) {
            String userId = jwt.getClaimAsString("user_id");
            return Long.valueOf(userId);
        }

        throw new RuntimeException("User not authenticated or invalid token");
    }

}

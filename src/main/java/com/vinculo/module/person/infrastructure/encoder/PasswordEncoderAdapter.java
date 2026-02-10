package com.vinculo.module.person.infrastructure.encoder;

import com.vinculo.module.person.domain.port.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderAdapter() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        if (password == null) {
            return null;
        }

        return bCryptPasswordEncoder.encode(password);
    }

}
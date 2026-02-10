package com.vinculo.module.person.domain.exception.email;

import com.vinculo.module.person.domain.exception.PersonException;

public class EmailAlreadyInUseException extends PersonException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    public EmailAlreadyInUseException() {
        super("Email is already in use.");
    }
}

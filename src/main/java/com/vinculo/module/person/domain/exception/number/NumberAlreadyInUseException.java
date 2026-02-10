package com.vinculo.module.person.domain.exception.number;

import com.vinculo.module.person.domain.exception.PersonException;

public class NumberAlreadyInUseException extends PersonException {
    public NumberAlreadyInUseException(String message) {
        super(message);
    }

    public NumberAlreadyInUseException() {
        super("The number is already in use by another person.");
    }

}

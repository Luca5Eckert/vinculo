package com.vinculo.module.person.domain.exception.number;

import com.vinculo.module.person.domain.exception.PersonException;

public class PhoneNumberIsNotValidException extends PersonException {
    public PhoneNumberIsNotValidException(String message) {
        super(message);
    }

    public PhoneNumberIsNotValidException() {
        super("Phone number is not valid");
    }
}

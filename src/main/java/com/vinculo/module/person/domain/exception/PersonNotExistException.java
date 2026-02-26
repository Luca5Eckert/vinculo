package com.vinculo.module.person.domain.exception;

import com.vinculo.module.request_connection.domain.exception.RequestConnectionException;

public class PersonNotExistException extends RequestConnectionException {
    public PersonNotExistException() {
        super("Person not exists.");
    }
}

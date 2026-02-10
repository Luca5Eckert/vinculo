package com.vinculo.module.person.domain.exception;

public class PersonNotExistException extends RuntimeException {
    public PersonNotExistException() {
        super("Person not exists.");
    }
}

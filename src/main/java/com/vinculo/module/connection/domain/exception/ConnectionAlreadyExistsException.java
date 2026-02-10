package com.vinculo.module.connection.domain.exception;

public class ConnectionAlreadyExistsException extends RuntimeException
{
    public ConnectionAlreadyExistsException() {
        super("Connection already exists.");
    }
}

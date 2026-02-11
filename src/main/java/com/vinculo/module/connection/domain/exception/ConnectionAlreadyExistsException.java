package com.vinculo.module.connection.domain.exception;

public class ConnectionAlreadyExistsException extends ConnectionException
{
    public ConnectionAlreadyExistsException() {
        super("Connection already exists.");
    }
}

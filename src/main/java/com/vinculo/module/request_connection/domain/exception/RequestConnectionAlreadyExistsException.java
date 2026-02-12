package com.vinculo.module.request_connection.domain.exception;

public class RequestConnectionAlreadyExistsException extends RequestConnectionException {
    public RequestConnectionAlreadyExistsException(String message) {
        super(message);
    }

    public RequestConnectionAlreadyExistsException() {
        super("A connection request already exists between these persons.");
    }
}

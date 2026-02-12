package com.vinculo.module.request_connection.domain.exception;

public class RequestConnectionNotExistException extends RequestConnectionException {
    public RequestConnectionNotExistException(String message) {
        super(message);
    }

    public RequestConnectionNotExistException() {
        super("Request connection does not exist.");
    }
}

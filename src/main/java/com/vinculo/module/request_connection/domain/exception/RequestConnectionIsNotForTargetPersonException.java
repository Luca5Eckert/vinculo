package com.vinculo.module.request_connection.domain.exception;

public class RequestConnectionIsNotForTargetPersonException extends RequestConnectionException {
    public RequestConnectionIsNotForTargetPersonException(String message) {
        super(message);
    }

    public RequestConnectionIsNotForTargetPersonException(){
      super("The request connection is not for the target person.");
    }
}

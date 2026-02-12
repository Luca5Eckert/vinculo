package com.vinculo.module.request_connection.domain.exception;

public class RequesterAndTargetCannotBeTheSameException extends RequestConnectionException {
    public RequesterAndTargetCannotBeTheSameException(String message) {
        super(message);
    }

    public RequesterAndTargetCannotBeTheSameException(){
        super("Requester and target cannot be the same user.");
    }
}

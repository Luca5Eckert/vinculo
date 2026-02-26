package com.vinculo.module.post.domain.exception;

public class PostAccessDeniedException extends PostDomainException {
    public PostAccessDeniedException(String message) {
        super(message);
    }

    public PostAccessDeniedException(String viewerId, String authorId) {
        super("User with ID " + viewerId + " does not have access to the post authored by user with ID " + authorId);
    }
}

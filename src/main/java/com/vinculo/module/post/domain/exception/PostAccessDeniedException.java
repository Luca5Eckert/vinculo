package com.vinculo.module.post.domain.exception;

public class PostAccessDeniedException extends PostDomainException {
    public PostAccessDeniedException(String message) {
        super(message);
    }

    public PostAccessDeniedException(Long viewerId, Long authorId) {
        super("User with ID " + viewerId + " does not have access to the post authored by user with ID " + authorId);
    }
}

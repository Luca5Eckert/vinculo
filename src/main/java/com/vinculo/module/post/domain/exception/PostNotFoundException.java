package com.vinculo.module.post.domain.exception;

public class PostNotFoundException extends PostDomainException {
    public PostNotFoundException() {
        super("Post not found");
    }
}

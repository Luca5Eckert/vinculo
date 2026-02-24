package com.vinculo.module.post.domain.command;

public record CreatePostCommand(
        String content,
        long personId
) {
    public static CreatePostCommand of(String content, long personId) {
        return new CreatePostCommand(content, personId);
    }
}

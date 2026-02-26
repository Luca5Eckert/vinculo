package com.vinculo.module.post.domain.command;

public record CreatePostCommand(
        String content,
        String personId
) {
    public static CreatePostCommand of(String content, String personId) {
        return new CreatePostCommand(content, personId);
    }
}

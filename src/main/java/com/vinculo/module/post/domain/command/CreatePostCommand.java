package com.vinculo.module.post.domain.command;

public record CreatePostCommand(
        long userId,
        String content
) {
}

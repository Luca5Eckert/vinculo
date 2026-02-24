package com.vinculo.module.post.domain.command;

public record GetAllByAuthorCommand(
        long authorId,
        int limit,
        int skip
) {
}

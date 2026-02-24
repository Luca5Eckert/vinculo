package com.vinculo.module.post.domain.command;

public record GetAllByAuthorCommand(
        long authorId,
        int limit,
        int skip
) {
    public static GetAllByAuthorCommand of(long authorId, int limit, int skip) {
        return new GetAllByAuthorCommand(
                authorId,
                limit,
                skip
        );
    }
}

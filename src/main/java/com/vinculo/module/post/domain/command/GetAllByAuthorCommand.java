package com.vinculo.module.post.domain.command;

public record GetAllByAuthorCommand(
        long personAuthenticatedId,
        long authorId,
        int limit,
        int skip
) {
    public static GetAllByAuthorCommand of(long personAuthenticatedId, long authorId, int limit, int skip) {
        return new GetAllByAuthorCommand(
                personAuthenticatedId,
                authorId,
                limit,
                skip
        );
    }
}

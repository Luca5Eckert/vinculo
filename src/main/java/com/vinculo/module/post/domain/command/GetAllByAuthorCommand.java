package com.vinculo.module.post.domain.command;

public record GetAllByAuthorCommand(
        String personAuthenticatedId,
        String authorId,
        int limit,
        int skip
) {
    public static GetAllByAuthorCommand of(String personAuthenticatedId, String authorId, int limit, int skip) {
        return new GetAllByAuthorCommand(
                personAuthenticatedId,
                authorId,
                limit,
                skip
        );
    }
}

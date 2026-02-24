package com.vinculo.module.post.domain.command;

public record GetAllForPersonCommand(
        long personId,
        int limit,
        int skip
) {

    public static GetAllForPersonCommand of(long personId, int limit, int skip){
        return new GetAllForPersonCommand(personId, limit, skip);
    }
}

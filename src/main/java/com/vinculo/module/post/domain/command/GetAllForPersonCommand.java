package com.vinculo.module.post.domain.command;

public record GetAllForPersonCommand(
        String personId,
        int limit,
        int skip
) {

    public static GetAllForPersonCommand of(String personId, int limit, int skip){
        return new GetAllForPersonCommand(personId, limit, skip);
    }
}

package com.vinculo.module.post.domain.command;

public record DeletePostCommand(
        long postId,
        long personId
) {
    public static DeletePostCommand of(long postId, long personId) {
        return new DeletePostCommand(
                postId,
                personId
        );
    }

}

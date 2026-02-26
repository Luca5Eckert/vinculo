package com.vinculo.module.post.domain.command;

public record DeletePostCommand(
        String postId,
        String personId
) {
    public static DeletePostCommand of(String postId, String personId) {
        return new DeletePostCommand(
                postId,
                personId
        );
    }

}

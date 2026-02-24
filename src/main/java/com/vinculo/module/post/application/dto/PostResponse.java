package com.vinculo.module.post.application.dto;

import java.time.LocalDateTime;

public record PostResponse(
        long id,
        String content,
        LocalDateTime createdAt,
        long authorId
) {
}

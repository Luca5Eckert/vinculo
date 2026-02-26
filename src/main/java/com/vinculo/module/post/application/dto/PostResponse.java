package com.vinculo.module.post.application.dto;

import java.time.LocalDateTime;

public record PostResponse(
        String id,
        String content,
        LocalDateTime createdAt,
        String authorId
) {
}

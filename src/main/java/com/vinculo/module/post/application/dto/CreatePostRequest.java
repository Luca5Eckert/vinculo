package com.vinculo.module.post.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
        @NotBlank String content
) {
}

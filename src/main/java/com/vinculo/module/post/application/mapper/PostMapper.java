package com.vinculo.module.post.application.mapper;

import com.vinculo.module.post.application.dto.PostResponse;
import com.vinculo.module.post.domain.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse of(Post post){
        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getCreatedAt(),
                post.getAuthor().getId()
        );
    }

}

package com.vinculo.module.post.domain.ports;

import com.vinculo.module.post.domain.model.Post;

public interface PostRepository {
    Post save(Post post);
}

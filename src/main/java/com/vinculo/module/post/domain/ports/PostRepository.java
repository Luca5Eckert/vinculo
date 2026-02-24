package com.vinculo.module.post.domain.ports;

import com.vinculo.module.post.domain.model.Post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(long id);

    void deleteById(long id);
}

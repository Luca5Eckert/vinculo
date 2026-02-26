package com.vinculo.module.post.domain.ports;

import com.vinculo.module.post.domain.model.Post;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(String id);

    void deleteById(String id);

    Page<Post> findNetworkFeed(String personId, int limit, int skip);

    Page<Post> findAllByAuthorId(String authorId, int limit, int skip);
}

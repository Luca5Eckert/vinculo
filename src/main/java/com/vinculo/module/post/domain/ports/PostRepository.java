package com.vinculo.module.post.domain.ports;

import com.vinculo.module.post.domain.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(long id);

    void deleteById(long id);

    List<Post> findNetworkFeed(long l, int limit, int skip);
}

package com.vinculo.module.post.infrastructure.persistence;

import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryAdapter implements PostRepository {

    private final PostRepositoryNeo4j postRepositoryNeo4j;

    public PostRepositoryAdapter(PostRepositoryNeo4j postRepositoryNeo4j) {
        this.postRepositoryNeo4j = postRepositoryNeo4j;
    }

    @Override
    public Post save(Post post) {
        return postRepositoryNeo4j.save(post);
    }

    @Override
    public Optional<Post> findById(long id) {
        return postRepositoryNeo4j.findById(id);
    }

    @Override
    public void deleteById(long id) {
        postRepositoryNeo4j.deleteById(id);
    }

    @Override
    public List<Post> findNetworkFeed(long personId, int limit, int skip) {
        return postRepositoryNeo4j.findNetworkFeed(
                personId,
                limit,
                skip
        );
    }
}

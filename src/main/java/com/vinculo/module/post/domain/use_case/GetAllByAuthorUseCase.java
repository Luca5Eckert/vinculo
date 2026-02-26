package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.post.domain.command.GetAllByAuthorCommand;
import com.vinculo.module.post.domain.exception.PostAccessDeniedException;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.policy.PostVisibilityPolicy;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetAllByAuthorUseCase {

    private final PostRepository postRepository;
    private final PostVisibilityPolicy postVisibilityPolicy;

    public GetAllByAuthorUseCase(PostRepository postRepository, PostVisibilityPolicy postVisibilityPolicy) {
        this.postRepository = postRepository;
        this.postVisibilityPolicy = postVisibilityPolicy;
    }

    /**
     * Retrieves a paginated list of posts by a specific author,
     * enforcing social connection visibility rules.
     */
    public Page<Post> execute(GetAllByAuthorCommand command) {
        validateVisibility(command.personAuthenticatedId(), command.authorId());

        return postRepository.findAllByAuthorId(
                command.authorId(),
                command.limit(),
                command.skip()
        );
    }

    private void validateVisibility(String viewerId, String authorId) {
        if (!postVisibilityPolicy.canView(viewerId, authorId)) {
            throw new PostAccessDeniedException(viewerId, authorId);
        }
    }

}
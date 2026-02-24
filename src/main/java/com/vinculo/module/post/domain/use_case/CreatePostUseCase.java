package com.vinculo.module.post.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.post.domain.command.CreatePostCommand;
import com.vinculo.module.post.domain.model.Post;
import com.vinculo.module.post.domain.ports.PostRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreatePostUseCase {

    private final PostRepository postRepository;
    private final PersonRepository personRepository;

    public CreatePostUseCase(PostRepository postRepository, PersonRepository personRepository) {
        this.postRepository = postRepository;
        this.personRepository = personRepository;
    }

    public Post execute(CreatePostCommand command){
        Person person = personRepository.findById(command.userId())
                .orElseThrow(PersonNotExistException::new);

        Post post = Post.builder()
                .content(command.content())
                .author(person)
                .createdAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }
}

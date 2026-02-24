package com.vinculo.module.post.domain.model;

import com.vinculo.module.person.domain.model.Person;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;


@Node("post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String content;

    private LocalDateTime createdAt;

    @Relationship(type = "POSTED", direction = Relationship.Direction.INCOMING)
    private Person author;


}

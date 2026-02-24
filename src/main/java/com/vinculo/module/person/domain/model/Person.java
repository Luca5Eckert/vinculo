package com.vinculo.module.person.domain.model;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.post.domain.model.Post;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("person")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    @Builder.Default
    private RoleUser role = RoleUser.NORMAL;

    @Relationship(type = "CONNECTED_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Connection> connections = new HashSet<>();

    @Relationship(type = "POSTED", direction = Relationship.Direction.OUTGOING)
    private Set<Post> posts = new HashSet<>();

    public void update(String name, String phoneNumber) {
        if(name != null && !name.isEmpty()) {
            this.name = name;
        }
        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }


    public void addConnection(Connection connection) {
        connections.add(connection);
    }

}

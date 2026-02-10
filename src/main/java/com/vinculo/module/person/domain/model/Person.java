package com.vinculo.module.person.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("person")
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
    private List<Person> connections;

    public Person() {
    }

    public void update(String name, String phoneNumber) {
        if(name != null && !name.isEmpty()) {
            this.name = name;
        }
        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }
}

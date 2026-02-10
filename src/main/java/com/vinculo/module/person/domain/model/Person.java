package com.vinculo.module.person.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("person")
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String number;

    @Relationship(type = "CONNECTED_WITH", direction = Relationship.Direction.OUTGOING)
    private List<Person> connections;

    public Person() {
    }

    public Person(String name, String number) {
        this.name = name;
        this.number = number;
    }

}

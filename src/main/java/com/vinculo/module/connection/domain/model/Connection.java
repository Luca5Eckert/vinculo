package com.vinculo.module.connection.domain.model;


import com.vinculo.module.person.domain.model.Person;
import lombok.*;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Connection {

    @RelationshipId
    private String id;

    @TargetNode
    private Person person;

    private TypeConnection type;

    private int weight;

    public Connection(Person person, TypeConnection type) {
        this.person = person;
        this.type = type;
        this.weight = type.getWeight();
    }

}

package com.vinculo.module.connection.domain.model;


import com.vinculo.module.person.domain.model.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Builder
@Setter
@Getter
public class Connection {

    @RelationshipId
    private Long id;

    @TargetNode
    private Person person;

    private TypeConnection type;

    public Connection() {
    }

    public Connection(Person person, TypeConnection type) {
        this.person = person;
        this.type = type;
    }

}

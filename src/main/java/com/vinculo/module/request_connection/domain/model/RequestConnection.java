package com.vinculo.module.request_connection.domain.model;

import com.vinculo.module.connection.domain.model.TypeConnection;
import com.vinculo.module.person.domain.model.Person;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Node("RequestConnection")
@Getter @Setter
@Builder
public class RequestConnection {

    @Id @GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
    private String id;

    private LocalDateTime createdAt;

    private TypeConnection type;

    private StatusRequestConnection status;

    @Relationship(type = "REQUESTED_BY", direction = Relationship.Direction.OUTGOING)
    private Person requester;

    @Relationship(type = "SENT_TO", direction = Relationship.Direction.OUTGOING)
    private Person target;

}
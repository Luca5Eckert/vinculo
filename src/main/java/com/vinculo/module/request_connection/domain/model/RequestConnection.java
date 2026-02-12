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
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestConnection {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdAt;

    private TypeConnection type;

    @Relationship(type = "FROM", direction = Relationship.Direction.INCOMING)
    private Person requester;

    @Relationship(type = "TO", direction = Relationship.Direction.OUTGOING)
    private Person target;

    private StatusRequestConnection status;
}
package com.vinculo.module.person.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinculo.module.connection.domain.model.Connection;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("Person")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phoneNumber;

    @Builder.Default
    private RoleUser role = RoleUser.NORMAL;

    @Relationship(type = "CONNECTED_WITH", direction = Relationship.Direction.OUTGOING)
    private final Set<Connection> connections = new HashSet<>();

    public void updateProfile(String name, String phoneNumber) {
        if (name != null && !name.isBlank()) this.name = name;
        if (phoneNumber != null && !phoneNumber.isBlank()) this.phoneNumber = phoneNumber;
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public List<Person> getConnectedPeople() {
        return connections.stream()
                .map(Connection::getPerson)
                .toList();
    }
}
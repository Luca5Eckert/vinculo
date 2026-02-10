package com.vinculo.module.domain.model;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class Connection {

    @RelationshipId
    private Long id;

}

package com.vinculo.module.graph.infrastructure.persistence.repository;

import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepositoryNeo4j extends Neo4jRepository<Person, String> {
    // Uses standard findById from Neo4jRepository which automatically loads relationships
}

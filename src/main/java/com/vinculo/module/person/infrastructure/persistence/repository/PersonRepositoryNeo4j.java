package com.vinculo.module.person.infrastructure.persistence.repository;

import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositoryNeo4j extends Neo4jRepository<Person, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String number);

    @Query("""
            MATCH (p1:person), (p2:person)
            WHERE id(p1) = $personId AND id(p2) = $connectedPersonId
            RETURN EXISTS((p1)-[:CONNECTED_WITH]->(p2))
            """)
    boolean existsConnectionBetween(Long personId, Long connectedPersonId);

    Optional<Person> findByEmail(String email);

    @Override
    @Query("""
        MATCH (p:Person) WHERE id(p) = $personId
        OPTIONAL MATCH (p)-[:FROM|TO]-(r:RequestConnection)
        DETACH DELETE r, p
    """)
    void deleteById(Long id);

}

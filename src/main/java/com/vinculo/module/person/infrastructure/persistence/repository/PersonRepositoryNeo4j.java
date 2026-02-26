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
    Optional<Person> findByEmail(String email);

    @Query("""
            MATCH (p1:person), (p2:person)
            WHERE id(p1) = $personId AND id(p2) = $connectedPersonId
            RETURN COUNT { (p1)-[:CONNECTED_WITH]-(p2) } > 0
            """)
    boolean existsConnectionBetween(Long personId, Long connectedPersonId);

    @Override
    @Query("""
        MATCH (p:person) WHERE id(p) = $id
        OPTIONAL MATCH (p)-[r]-()
        DETACH DELETE p, r
    """)
    void deleteById(Long id);

    @Query("MATCH (p1:Person {id: $authId})-[:CONNECTED_WITH]-(p2:Person {id: $targetId}) RETURN count(p2) > 0")
    boolean isConnected(Long authId, Long personId);
}
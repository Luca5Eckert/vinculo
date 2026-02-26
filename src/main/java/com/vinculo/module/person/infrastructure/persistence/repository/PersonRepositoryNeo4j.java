package com.vinculo.module.person.infrastructure.persistence.repository;

import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositoryNeo4j extends Neo4jRepository<Person, String> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String number);
    Optional<Person> findByEmail(String email);

    @Query("""
            MATCH (p1:Person), (p2:Person)
            WHERE p1.id = $personId AND p2.id = $connectedPersonId
            RETURN COUNT { (p1)-[:CONNECTED_WITH]-(p2) } > 0
            """)
    boolean existsConnectionBetween(String personId, String connectedPersonId);

    @Override
    @Query("""
        MATCH (p:Person) WHERE p.id = $id
        OPTIONAL MATCH (p)-[r]-()
        DETACH DELETE p, r
    """)
    void deleteById(String id);

    @Query("MATCH (p1:Person {id: $authId})-[:CONNECTED_WITH]-(p2:Person {id: $targetId}) RETURN count(p2) > 0")
    boolean isConnected(String authId, String targetId);
}
package com.vinculo.module.person.infrastructure.persistence.repository;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepositoryNeo4j extends Neo4jRepository<Person, String> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String number);
    Optional<Person> findByEmail(String email);


    @Override
    @Query("""
        MATCH (p:Person) WHERE p.id = $id
        OPTIONAL MATCH (p)-[r]-()
        DETACH DELETE p, r
    """)
    void deleteById(String id);

    @Query("MATCH (p1:Person {id: $authId})-[:CONNECTED_WITH]-(p2:Person {id: $targetId}) RETURN count(p2) > 0")
    boolean isConnected(String authId, String targetId);

    @Query("""
            MATCH (p1:Person {id: $personId})-[r:CONNECTED_WITH]->(p2:Person)
            RETURN r, p1, p2
            """)
    List<Connection> findAllConnectionsByPersonId(@Param("personId") String personId);

    @Query("MATCH (a:Person {id: $id1})-[:CONNECTED_WITH]-(b:Person {id: $id2}) RETURN COUNT(a) > 0")
    boolean existsConnectionBetween(
            @Param("id1") String personId,
            @Param("id2") String targetPersonId
    );

}
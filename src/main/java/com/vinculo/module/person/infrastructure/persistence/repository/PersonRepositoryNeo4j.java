package com.vinculo.module.person.infrastructure.persistence.repository;

import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositoryNeo4j extends Neo4jRepository<Person, Long> {

    boolean existByEmail(String email);

    boolean existByNumber(String number);


    @Query("MATCH (p1:Person)-[:CONNECTED_WITH]-(p2:Person) " +
            "WHERE id(p1) = $personId AND id(p2) = $connectedPersonId " +
            "RETURN count(*) > 0")
    boolean existsConnectionBetween(Long personId, Long connectedPersonId);

}

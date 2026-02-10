package com.vinculo.module.person.infrastructure.persistence.repository;

import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonRepositoryAdapter implements PersonRepository {

    private final PersonRepositoryNeo4j personRepositoryNeo4j;

    public PersonRepositoryAdapter(PersonRepositoryNeo4j personRepositoryNeo4j) {
        this.personRepositoryNeo4j = personRepositoryNeo4j;
    }

    @Override
    public boolean existsByNumber(String number) {
        return personRepositoryNeo4j.existByNumber(number);
    }

    @Override
    public void save(Person person) {
        personRepositoryNeo4j.save(person);
    }

    @Override
    public boolean existsByEmail(String email) {
        return personRepositoryNeo4j.existByEmail(email);
    }

    @Override
    public boolean existsById(Long personId) {
        return personRepositoryNeo4j.existsById(personId);
    }

    @Override
    public void deleteById(Long personId) {
        personRepositoryNeo4j.deleteById(personId);
    }

    @Override
    public Optional<Person> findById(Long personId) {
        return personRepositoryNeo4j.findById(personId);
    }

    @Override
    public Optional<Person> findByEmail(String username) {
        return Optional.empty();
    }

}

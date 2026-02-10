package com.vinculo.module.person.domain.port;

import com.vinculo.module.person.domain.model.Person;

import java.util.Optional;

public interface PersonRepository {

    boolean existsByNumber(String number);

    void save(Person person);

    boolean existsByEmail(String email);

    boolean existsById(Long personId);

    void deleteById(Long personId);

    Optional<Person> findById(Long personId);

    Optional<Person> findByEmail(String username);

    boolean existsConnectionBetween(Long personId, Long connectedPersonId);

}

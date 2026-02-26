package com.vinculo.module.person.domain.port;

import com.vinculo.module.person.domain.model.Person;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PersonRepository {

    boolean existsByPhoneNumber(String number);

    void save(Person person);

    boolean existsByEmail(String email);

    boolean existsById(String personId);

    void deleteById(String personId);

    Optional<Person> findById(String personId);

    Optional<Person> findByEmail(String username);

    boolean existsConnectionBetween(String personId, String connectedPersonId);

    Page<Person> findAll(int page, int size);

    boolean isConnected(String authId, String personId);
}

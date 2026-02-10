package com.vinculo.module.person.domain.port;


import com.vinculo.module.person.domain.model.Person;

public interface PersonRepository {

    boolean existsByNumber(String number);

    void save(Person person);

    boolean existsByEmail(String email);
}

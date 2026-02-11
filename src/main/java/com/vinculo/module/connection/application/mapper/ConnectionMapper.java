package com.vinculo.module.connection.application.mapper;

import com.vinculo.module.connection.application.dto.ConnectionResponse;
import com.vinculo.module.connection.application.dto.PersonResponseConnection;
import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.model.Person;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMapper {

    public ConnectionResponse toResponse(Connection connection) {
        PersonResponseConnection personResponseConnection = toPersonResponse(connection.getPerson());

        return new ConnectionResponse(
                personResponseConnection,
                connection.getType()
        );
    }

    private PersonResponseConnection toPersonResponse(Person person) {
        return new PersonResponseConnection(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getPhoneNumber()
        );
    }

}

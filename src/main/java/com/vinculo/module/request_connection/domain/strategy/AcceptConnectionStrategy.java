package com.vinculo.module.request_connection.domain.strategy;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import org.springframework.stereotype.Component;

@Component
public class AcceptConnectionStrategy implements RequestStatusStrategy {

    private final PersonRepository personRepository;

    public AcceptConnectionStrategy(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(StatusRequestConnection status) {
        return status == StatusRequestConnection.ACCEPTED;
    }

    @Override
    public void execute(RequestConnection request) {
        Person requester = request.getRequester();
        Person target = request.getTarget();

        requester.addConnection(new Connection(target, request.getType()));
        target.addConnection(new Connection(requester, request.getType()));

        personRepository.save(requester);
        personRepository.save(target);
    }

}
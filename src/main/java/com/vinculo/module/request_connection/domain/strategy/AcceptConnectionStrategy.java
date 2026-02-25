package com.vinculo.module.request_connection.domain.strategy;

import com.vinculo.module.connection.domain.model.Connection;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class AcceptConnectionStrategy implements RequestStatusStrategy {

    private final PersonRepository personRepository;
    private final RequestConnectionRepository requestConnectionRepository;

    public AcceptConnectionStrategy(PersonRepository personRepository, RequestConnectionRepository requestConnectionRepository) {
        this.personRepository = personRepository;
        this.requestConnectionRepository = requestConnectionRepository;
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

        requestConnectionRepository.delete(request);
    }

}
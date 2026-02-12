package com.vinculo.module.request_connection.domain.use_case;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import com.vinculo.module.request_connection.domain.command.SendRequestConnectionCommand;
import com.vinculo.module.request_connection.domain.exception.RequestConnectionAlreadyExistsException;
import com.vinculo.module.request_connection.domain.exception.RequesterAndTargetCannotBeTheSameException;
import com.vinculo.module.request_connection.domain.model.RequestConnection;
import com.vinculo.module.request_connection.domain.model.StatusRequestConnection;
import com.vinculo.module.request_connection.domain.port.RequestConnectionRepository;
import org.springframework.stereotype.Component;

@Component
public class SendRequestConnectionUseCase {

    private final RequestConnectionRepository requestConnectionRepository;
    private final PersonRepository personRepository;

    public SendRequestConnectionUseCase(RequestConnectionRepository requestConnectionRepository, PersonRepository personRepository) {
        this.requestConnectionRepository = requestConnectionRepository;
        this.personRepository = personRepository;
    }

    public void execute(SendRequestConnectionCommand command) {
        if(command.personRequesterId() == command.personTargetId()){
            throw new RequesterAndTargetCannotBeTheSameException();
        }

        Person personRequester = personRepository.findById(command.personRequesterId())
                .orElseThrow(PersonNotExistException::new);

        Person personTarget = personRepository.findById(command.personTargetId())
                .orElseThrow(PersonNotExistException::new);


        verifyExistingConnection(command.personRequesterId(), command.personTargetId());

        RequestConnection requestConnection = RequestConnection.builder()
                .requester(personRequester)
                .target(personTarget)
                .type(command.typeConnection())
                .status(StatusRequestConnection.PENDING)
                .build();

        requestConnectionRepository.save(requestConnection);
    }

    private void verifyExistingConnection(long personSenderId, long personReceiverId) {
        if(requestConnectionRepository.existsBySenderIdAndReceiverId(personSenderId, personReceiverId)){
            throw new RequestConnectionAlreadyExistsException();
        }
    }
}

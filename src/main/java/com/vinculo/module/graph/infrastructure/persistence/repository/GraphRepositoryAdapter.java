package com.vinculo.module.graph.infrastructure.persistence.repository;

import com.vinculo.module.graph.domain.model.Edge;
import com.vinculo.module.graph.domain.model.GraphNetwork;
import com.vinculo.module.graph.domain.model.Node;
import com.vinculo.module.graph.domain.port.GraphRepository;
import com.vinculo.module.graph.domain.query.GetGraphNetworkQuery;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.infrastructure.persistence.repository.PersonRepositoryNeo4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GraphRepositoryAdapter implements GraphRepository {

    private final PersonRepositoryNeo4j personRepositoryNeo4j;

    public GraphRepositoryAdapter(PersonRepositoryNeo4j personRepositoryNeo4j) {
        this.personRepositoryNeo4j = personRepositoryNeo4j;
    }

    @Override
    public GraphNetwork getGraphNetwork(String personId) {
        return buildGraphNetwork(personId);
    }

    @Override
    public GraphNetwork execute(GetGraphNetworkQuery query) {
        return buildGraphNetwork(query.personId());
    }

    private GraphNetwork buildGraphNetwork(String userId) {
        Optional<Person> personOpt = personRepositoryNeo4j.findById(userId);
        
        if (personOpt.isEmpty()) {
            return new GraphNetwork(List.of(), List.of());
        }

        Person person = personOpt.get();
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        // Add the main person as the first node
        nodes.add(new Node(
            person.getId(),
            person.getName(),
            person.getUsername()
        ));

        // Add connected people as nodes and create edges
        if (person.getConnections() != null) {
            person.getConnections().forEach(connection -> {
                Person connectedPerson = connection.getPerson();
                
                if (connectedPerson != null) {
                    // Add connected person as a node
                    nodes.add(new Node(
                        connectedPerson.getId(),
                        connectedPerson.getName(),
                        connectedPerson.getUsername()
                    ));

                    // Add edge from main person to connected person
                    edges.add(new Edge(
                        person.getId(),
                        connectedPerson.getId(),
                        connection.getType().name(),
                        connection.getWeight()
                    ));
                }
            });
        }

        return new GraphNetwork(nodes, edges);
    }
}

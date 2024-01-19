package org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult;

import java.util.*;

public class RaceResultsService {

    private final Map<Client, List<RaceCategory>> clients = new HashMap<>();
    private final Logger logger;

    public RaceResultsService(Logger logger) {
        this.logger = logger;
    }

    public void addSubscriber(Client client) {
        clients.put(client, Arrays.stream(RaceCategory.values()).toList());
    }

    public void addSubscriber(Client client, List<RaceCategory> raceCategory) {
        clients.put(client, raceCategory);
    }

    public void send(Message message) {
        logger.log(message.getDate(), message.getMessage());
        clients.forEach((client, raceCategories) -> {
            if (raceCategories.contains(message.getCategory())) {
                client.receive(message);
            }
        });
    }

    public void removeSubscriber(Client client) {
        if (clients.containsKey(client)) {
            clients.remove(client);
        } else throw new ClientNotSubscribedException("Client not subscribed");
    }
}


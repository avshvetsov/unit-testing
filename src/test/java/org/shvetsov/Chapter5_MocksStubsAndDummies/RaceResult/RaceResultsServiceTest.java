package org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Please enhance the Race Results example (see Section 5.4) with the following functionality:
 * <p>• {@link  org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult.RaceResultsService} should send messages with the results of different categories of race - e.g.
 * horse races, F1 races, boat-races, etc. Subscribers should be able to subscribe to selected categories.
 * Make sure they receive only messages related to the ones they have signed up for.
 * <p>• Each message sent by {@link  org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult.RaceResultsService} should be logged. Introduce a logging collaborator,
 * and make sure that the date and text of each message is logged. Do not implement the logging
 * mechanism: concentrate on the interactions between the service and its collaborator.
 * <p>• In the tests implemented so far, {@link  org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult.RaceResultsService} sends only one message. This is unrealistic!
 * Enhance the tests to ensure that subscribers will receive any number of sent messages.
 * <p>• What should happen if a client that is not subscribed tries to unsubscribe? Make up your mind about
 * it, write a test which verifies this behaviour, and make {@link  org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult.RaceResultsService} behave accordingly.
 */
@DisplayName("5.7.2. Race Results Enhanced")
class RaceResultsServiceTest {

    private final RaceResultsService raceResults = new RaceResultsService();
    private final Message message = mock(Message.class);
    private final Client clientA = mock(Client.class, "clientA");
    private final Client clientB = mock(Client.class, "clientB");

    @Test
    void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(message);
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }
    @Test
    void subscribedClientShouldReceiveMessage() {
        raceResults.addSubscriber(clientA);
        raceResults.send(message);
        verify(clientA).receive(message);
    }

    @Test
    void allSubscribedClientsShouldReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    @Test
    void shouldSendOnlyOneMessageToMultiSubscriber() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientA);
        raceResults.send(message);
        verify(clientA).receive(message);
    }

    @Test
    void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.removeSubscriber(clientA);
        raceResults.send(message);
        verify(clientA, never()).receive(message);
    }


}
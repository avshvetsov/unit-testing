package org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult.RaceCategory.*;

/**
 * Please enhance the Race Results example with the following functionality:
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

    private final Logger logger = mock(Logger.class);
    private final RaceResultsService raceResults = new RaceResultsService(logger);
    private final Message message = mock(Message.class, "default");
    private final Client clientA = mock(Client.class, "clientA");
    private final Client clientB = mock(Client.class, "clientB");

    private Message getMessageByCategory(RaceCategory category) {
        Message message = mock(Message.class, category.name());
        when(message.getCategory()).thenReturn(category);
        return message;
    }

    @BeforeEach
    void setUp() {
        RaceCategory[] categories = values();
        RaceCategory randomRace = categories[ThreadLocalRandom.current().nextInt(0, categories.length)];
        when(message.getCategory()).thenReturn(randomRace);
    }

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

    @Test
    void subscribedClientShouldReceiveMessageFromRelatedCategory() {
        raceResults.addSubscriber(clientA, List.of(F1));
        Message f1Message = getMessageByCategory(F1);
        Message horseMessage = getMessageByCategory(HORSE);
        raceResults.send(f1Message);
        raceResults.send(horseMessage);
        verify(clientA, times(1)).receive(f1Message);
    }

    @Test
    void subscribedClientShouldNotReceiveMessageFromAnotherCategory() {
        raceResults.addSubscriber(clientA, List.of(F1));
        Message horseMessage = getMessageByCategory(HORSE);
        raceResults.send(horseMessage);
        verify(clientA, never()).receive(horseMessage);
    }

    @Test
    void eachMessageShouldBeLogged() {
        LocalDate now = LocalDate.now();
        String messageText = "some text";
        when(message.getDate()).thenReturn(now);
        when(message.getMessage()).thenReturn(messageText);
        raceResults.send(message);
        verify(logger).log(now,messageText);
    }

    @Test
    void eachMessageShouldBeLoggedOnlyOnes() {
        LocalDate now = LocalDate.now();
        String messageText = "some text";
        when(message.getDate()).thenReturn(now);
        when(message.getMessage()).thenReturn(messageText);
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);
        verify(logger).log(now,messageText);
    }

    @Test
    void subscribedClientMultipleCategoriesShouldReceiveAllMessages() {
        raceResults.addSubscriber(clientA, List.of(F1, HORSE));
        raceResults.addSubscriber(clientB, List.of(BOAT, HORSE));
        Message f1Message = getMessageByCategory(F1);
        Message horseMessage = getMessageByCategory(HORSE);
        Message boatMessage = getMessageByCategory(BOAT);
        raceResults.send(f1Message);
        raceResults.send(horseMessage);
        raceResults.send(boatMessage);
        verify(clientA, never()).receive(boatMessage);
        verify(clientB, never()).receive(f1Message);
        verify(clientA, times(2)).receive(any());
        verify(clientB, times(2)).receive(any());
    }

    @Test
    void removeUnsubscribedClientShouldThrowClientNotSubscribedException() {
        assertThatThrownBy(() -> raceResults.removeSubscriber(clientA)).isInstanceOf(ClientNotSubscribedException.class);
        assertThatExceptionOfType(ClientNotSubscribedException.class).isThrownBy(() -> raceResults.removeSubscriber(clientA));
    }

}
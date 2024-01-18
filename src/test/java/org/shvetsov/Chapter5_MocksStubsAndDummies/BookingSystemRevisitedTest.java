package org.shvetsov.Chapter5_MocksStubsAndDummies;

import org.junit.jupiter.api.DisplayName;

/**
 * This booking system allows classrooms to be booked. Each classroom has a certain capacity (e.g. for
 * 20 people), and can be equipped with an overhead projector, microphone and whiteboard. It also has
 * a unique name (ID, number, whatever you wish to call it…). The API of the system should allow one
 * to:
 * <p>• list all existing classrooms,
 * <p>• list all available classrooms (for a given day and hourly time slot),
 * <p>• book a specific classroom by name (e.g. "I want to book classroom A1": book("A1")),
 * <p>• book a specific classroom by specifying some constraints (e.g. "I want to book a classroom with a
 * projector for 20 people": book(20, Equipment.PROJECTOR)).
 * Here are some additional constraints, so the system is not too complex at first:
 * <p>• only periodical booking is supported, which means you can book, for example, classroom A1 for
 * every Friday from 10 to 11 am, but not just for Friday the 13th of May.
 * <p>• each booking lasts for 1 hour; no longer or shorter periods are allowed.
 * Once you have implemented the system specified above, use your imagination and add some more
 * complexity. For example:
 * <p>• each booking operation should be written to logs,
 * <p>• each classroom has a "cleaning hour", when it is not available,
 * <p>• the booking time is no longer limited to 1 hour
 */
@DisplayName("5.7.3. Booking System Revisited")
class BookingSystemRevisitedTest {

}
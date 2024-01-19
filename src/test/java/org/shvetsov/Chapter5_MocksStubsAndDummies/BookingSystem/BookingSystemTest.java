package org.shvetsov.Chapter5_MocksStubsAndDummies.BookingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
 * <p>Here are some additional constraints, so the system is not too complex at first:
 * <p>• only periodical booking is supported, which means you can book, for example, classroom A1 for
 * every Friday from 10 to 11 am, but not just for Friday the 13th of May.
 * <p>• each booking lasts for 1 hour; no longer or shorter periods are allowed.
 * <p>Once you have implemented the system specified above, use your imagination and add some more
 * complexity. For example:
 * <p>• each booking operation should be written to logs,
 * <p>• each classroom has a "cleaning hour", when it is not available,
 * <p>• the booking time is no longer limited to 1 hour
 */
@DisplayName("5.7.3. Booking System Revisited")
class BookingSystemTest {

    public static final TimeSlot DEFAULT_TIME_SLOT = new TimeSlot(DayOfWeek.MONDAY, 11);
    public static final TimeSlot DIFFERENT_TIME_SLOT = new TimeSlot(DayOfWeek.THURSDAY, 11);
    public static final ClassRoom TORONTO = new ClassRoom("Toronto", 10, Collections.emptyList());
    public static final ClassRoom TOKYO = new ClassRoom("Tokyo", 100, List.of(Equipment.MICROPHONE));
    public static final ClassRoom NEW_YORK = new ClassRoom("NewYork", 20, List.of(Equipment.PROJECTOR));
    public static final ClassRoom LONDON = new ClassRoom("London", 5, List.of(Equipment.PROJECTOR, Equipment.WHITEBOARD));
    public static final List<ClassRoom> DEFAULT_ROOM_LIST = List.of(TORONTO, TOKYO, NEW_YORK, LONDON);

    private final ClassroomManagementSystem classrooms = mock(ClassroomManagementSystem.class);
    private BookingSystem bookingSystem;

    @BeforeEach
    void setUp() {
        when(classrooms.getAllClassRooms()).thenReturn(DEFAULT_ROOM_LIST);
        bookingSystem = new BookingSystem(classrooms);
    }

    @Test
    void shouldReturnAllClassrooms() {
        assertThat(bookingSystem.getAllClassrooms()).hasSameElementsAs(DEFAULT_ROOM_LIST);
    }

    @Test
    void shouldReturnAvailableClassrooms() {
        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).hasSameElementsAs(DEFAULT_ROOM_LIST);
    }

    @Test
    void shouldAllowBookClassrooms() {
        assertThat(bookingSystem.book("Toronto", DEFAULT_TIME_SLOT)).isEqualTo(TORONTO);
        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(TORONTO);
    }

    @Test
    void shouldNotAllowDoubleBookClassrooms() {
        assertThat(bookingSystem.book("Toronto", DEFAULT_TIME_SLOT)).isEqualTo(TORONTO);
        assertThatThrownBy(() -> bookingSystem.book("Toronto", DEFAULT_TIME_SLOT)).isInstanceOf(NoSuchElementException.class);
        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(TORONTO);
    }

    @Test
    void shouldThrowExceptionIfClassroomDoesNotFindByName() {
        assertThatThrownBy(() -> bookingSystem.book("Illegal Name", DEFAULT_TIME_SLOT)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldAllowBookClassroomBySize() {
        assertThat(bookingSystem.book(15, DEFAULT_TIME_SLOT)).isEqualTo(NEW_YORK);
        assertThat(bookingSystem.book(15, DEFAULT_TIME_SLOT)).isEqualTo(TOKYO);
        assertThatThrownBy(() -> bookingSystem.book(15, DEFAULT_TIME_SLOT)).isInstanceOf(NoSuchElementException.class);

        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(NEW_YORK, TOKYO);
    }

    @Test
    void shouldAllowBookClassroomByEquipment() {
        assertThat(bookingSystem.book(Equipment.PROJECTOR, DEFAULT_TIME_SLOT)).isEqualTo(LONDON);
        assertThat(bookingSystem.book(Equipment.PROJECTOR, DEFAULT_TIME_SLOT)).isEqualTo(NEW_YORK);
        assertThatThrownBy(() -> bookingSystem.book(Equipment.PROJECTOR, DEFAULT_TIME_SLOT)).isInstanceOf(NoSuchElementException.class);

        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(NEW_YORK, LONDON);
    }

    @Test
    void shouldAllowBookClassroomByEquipmentAndCapacity() {
        assertThat(bookingSystem.book(Equipment.PROJECTOR, 10, DEFAULT_TIME_SLOT)).isEqualTo(NEW_YORK);
        assertThatThrownBy(() -> bookingSystem.book(Equipment.PROJECTOR, 10, DEFAULT_TIME_SLOT)).isInstanceOf(NoSuchElementException.class);

        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(NEW_YORK);
    }

    @Test
    void shouldAllowBookSameClassroomForDifferentTimeSlot() {
        assertThat(bookingSystem.book(99, DEFAULT_TIME_SLOT)).isEqualTo(TOKYO);
        assertThat(bookingSystem.getAvailableClassrooms(DIFFERENT_TIME_SLOT)).hasSameElementsAs(DEFAULT_ROOM_LIST);

        assertThat(bookingSystem.book(99, DIFFERENT_TIME_SLOT)).isEqualTo(TOKYO);
        assertThat(bookingSystem.getAvailableClassrooms(DEFAULT_TIME_SLOT)).doesNotContain(TOKYO);
        assertThat(bookingSystem.getAvailableClassrooms(DIFFERENT_TIME_SLOT)).doesNotContain(TOKYO);
    }
}
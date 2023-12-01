package org.shvetsov.Chapter4_TestDrivenDevelopment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Your task is to write a (very) simplified version of a booking system. In fact, it can be written as a
 * single class, which should:
 * <p>• return a list of booked hours,
 * <p>• not allow a particular hour to be double-booked,
 * <p>• deal in a sensible manner with illegal values (provided as input parameters).
 * <p>On the constraints side (to make the task more appropriate for practicing TDD), the system:
 * <p>• has only one resource that can be booked (e.g. a classroom, a lawn mower, a restaurant table, or
 * anything else that makes sense to you),
 * <p>• has no notion of days, or to put it differently, it assumes all reservations are for today,
 * <p>• should only permit booking of regular whole clock-hours (e.g. it should not allow a booking from
 * 4:30 pm. to 5:30 pm.),
 * <p>• is not required to remember any additional information concerning the reservation (who booked it,
 * when etc.).
 */
@DisplayName("4.11.3. Booking System")
class BookingSystemTest {

    BookingSystem bookingSystem;
    @BeforeEach
    void setUp() {
        bookingSystem = new BookingSystem();
    }

    @Test
    public void shouldBook() {
        assertThat(bookingSystem.book(1)).isTrue();
    }

    @Test
    public void shouldReturnBookingList() {
        bookingSystem.book(1);
        bookingSystem.book(3);
        bookingSystem.book(7);
        bookingSystem.book(23);
        assertThat(bookingSystem.getBookedList()).hasSameElementsAs(List.of(1, 3, 7, 23));
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> bookingSystem.book(77));
    }

    @Test
    public void shouldNotAllowDoubleBookHour() {
        boolean firstBookResult = bookingSystem.book(7);
        boolean doubleBookResult = bookingSystem.book(7);

        assertThat(firstBookResult).isTrue();
        assertThat(doubleBookResult).isFalse();
        assertThat(bookingSystem.getBookedList().size()).isEqualTo(1);
    }
}
package org.shvetsov.Chapter4_TestDrivenDevelopment;


import java.util.ArrayList;
import java.util.List;

public class BookingSystem {

    private final List<Integer> bookedHours = new ArrayList<>();

    public List<Integer> getBookedList() {
        return bookedHours;
    }

    public boolean book(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be in range from 0 to 23");
        }
        if (bookedHours.contains(hour)) {
            return false;
        }
        bookedHours.add(hour);
        return true;
    }
}
package org.shvetsov.Chapter5_MocksStubsAndDummies.BookingSystem;

import java.util.List;

public record ClassRoom(String name, int capacity, List<Equipment> equipments) {

}

package org.shvetsov.Chapter5_MocksStubsAndDummies.BookingSystem;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookingSystem {

    Map<ClassRoom, Set<TimeSlot>> bookingSchedule = new HashMap<>();

    public BookingSystem(ClassroomManagementSystem classrooms) {
        classrooms.getAllClassRooms().forEach(classRoom -> bookingSchedule.put(classRoom, new HashSet<>()));
    }

    public Collection<ClassRoom> getAllClassrooms() {
        return bookingSchedule.keySet();
    }

    public ClassRoom book(String classroomName, TimeSlot timeSlot) {
        ClassRoom classRoom = getClassRoomByName(classroomName);
        return book(classRoom, timeSlot);
    }

    public ClassRoom book(int size, TimeSlot timeSlot) {
        ClassRoom classRoom = getClassRoomBySize(size, timeSlot).stream()
                .min(Comparator.comparingInt(ClassRoom::capacity))
                .orElseThrow();
        return book(classRoom, timeSlot);
    }

    public ClassRoom book(Equipment equipment, TimeSlot timeSlot) {
        ClassRoom classRoom = getClassRoomByEquipment(equipment, timeSlot).stream()
                .min(Comparator.comparingInt(ClassRoom::capacity))
                .orElseThrow();
        return book(classRoom, timeSlot);
    }

    public ClassRoom book(Equipment equipment, int size, TimeSlot timeSlot) {
        List<ClassRoom> classRoomByEquipment = getClassRoomByEquipment(equipment, timeSlot);
        List<ClassRoom> classRoomBySize = getClassRoomBySize(size, timeSlot);
        classRoomByEquipment.retainAll(classRoomBySize);
        ClassRoom classRoom = classRoomByEquipment.stream()
                .min(Comparator.comparingInt(ClassRoom::capacity))
                .orElseThrow();
        return book(classRoom, timeSlot);
    }

    private ClassRoom book(ClassRoom classRoom, TimeSlot timeSlot) {
        if (bookingSchedule.get(classRoom).add(timeSlot)) {
            return classRoom;
        } else throw new NoSuchElementException("No available time slots");
    }

    private ClassRoom getClassRoomByName(String classroomName) {
        return bookingSchedule.keySet().stream()
                .filter(cr -> cr.name().equals(classroomName))
                .findAny()
                .orElseThrow();
    }

    private List<ClassRoom> getClassRoomBySize(int size, TimeSlot timeSlot) {
        return bookingSchedule.entrySet().stream()
                .filter(Predicate.not(entry -> entry.getValue().contains(timeSlot)))
                .map(Map.Entry::getKey)
                .filter(cr -> cr.capacity() > size)
                .collect(Collectors.toList());
    }

    private List<ClassRoom> getClassRoomByEquipment(Equipment equipment, TimeSlot timeSlot) {
        return bookingSchedule.entrySet().stream()
                .filter(Predicate.not(entry -> entry.getValue().contains(timeSlot)))
                .map(Map.Entry::getKey)
                .filter(cr -> cr.equipments().contains(equipment))
                .collect(Collectors.toList());
    }

    public Collection<ClassRoom> getAvailableClassrooms(TimeSlot timeSlot) {
        return bookingSchedule.entrySet().stream()
                .filter(Predicate.not(entry -> entry.getValue().contains(timeSlot)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

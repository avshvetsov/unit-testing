package org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult;

import java.time.LocalDate;

public interface Message {
    RaceCategory getCategory();

    LocalDate getDate();

    String getMessage();
}

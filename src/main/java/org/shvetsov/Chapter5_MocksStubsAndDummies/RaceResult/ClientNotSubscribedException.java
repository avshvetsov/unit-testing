package org.shvetsov.Chapter5_MocksStubsAndDummies.RaceResult;

public class ClientNotSubscribedException extends RuntimeException {

    public ClientNotSubscribedException(String message) {
        super(message);
    }
}

package mateuszsalwowski.zadanierekrutacyjne.exception;

public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException(String message) {
        super(message);
    }
}

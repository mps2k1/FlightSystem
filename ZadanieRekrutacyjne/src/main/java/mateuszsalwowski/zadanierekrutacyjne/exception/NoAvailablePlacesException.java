package mateuszsalwowski.zadanierekrutacyjne.exception;

public class NoAvailablePlacesException extends RuntimeException{
    public NoAvailablePlacesException(String message) {
        super(message);
    }
}

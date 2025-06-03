package de.epex.pokerhands.service.exception;

public class InvalidPokerHandException extends IllegalArgumentException {

    public InvalidPokerHandException(String message) {
        super(message);
    }

    public InvalidPokerHandException(String message, Throwable cause) {
        super(message, cause);
    }
}

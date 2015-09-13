package com.game.exceptions;


public class UnknownErrorException extends Exception {
    public UnknownErrorException() {
        super("Unknown error occurred. Please try again.");
    }
    public UnknownErrorException(String message) {
        super(message);
    }
}

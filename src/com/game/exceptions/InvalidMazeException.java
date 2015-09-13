package com.game.exceptions;

public class InvalidMazeException extends Exception {
    public InvalidMazeException() {
        super("Invalid file format. Please try again.");
    }
    public InvalidMazeException(String message) {
        super(message);
    }
}

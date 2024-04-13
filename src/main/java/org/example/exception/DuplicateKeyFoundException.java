package org.example.exception;

public class DuplicateKeyFoundException extends Exception {
    public DuplicateKeyFoundException(String errorMessage) {
        super(errorMessage);
    }
}

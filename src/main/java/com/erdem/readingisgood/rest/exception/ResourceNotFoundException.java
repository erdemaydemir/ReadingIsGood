package com.erdem.readingisgood.rest.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

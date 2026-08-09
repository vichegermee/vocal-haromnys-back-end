package com.vocalharmonys.backend.exception;

/** Thrown by a service when a lookup-by-id doesn't find anything. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

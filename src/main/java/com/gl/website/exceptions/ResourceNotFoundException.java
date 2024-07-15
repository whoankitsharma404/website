package com.gl.website.exceptions;

/**
 * This class is used to handle the exceptions that occur
 * when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

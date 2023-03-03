package com.ecore.roles.exception;

import static java.lang.String.format;

public class InvalidObjectException extends RuntimeException {

    public <T> InvalidObjectException(Class<T> resource, String message) {
        super(format("Invalid '%s' object. %s.", resource.getSimpleName(), message));
    }
}

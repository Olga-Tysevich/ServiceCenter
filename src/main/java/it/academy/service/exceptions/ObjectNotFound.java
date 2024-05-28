package it.academy.service.exceptions;

public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound(String message) {
        super(message);
    }

    public ObjectNotFound() {
    }
}

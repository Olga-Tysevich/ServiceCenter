package it.academy.service.exceptions;

public class ObjectAlreadyExist extends RuntimeException {

    public ObjectAlreadyExist(String message) {
        super(message);
    }

    public ObjectAlreadyExist() {
    }
}

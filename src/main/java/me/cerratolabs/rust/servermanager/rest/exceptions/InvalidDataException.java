package me.cerratolabs.rust.servermanager.rest.exceptions;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

}
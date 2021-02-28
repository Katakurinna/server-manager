package me.cerratolabs.rust.servermanager.rest.exceptions;

public class ServerAlreadyExistException extends RuntimeException {
    public ServerAlreadyExistException(String s) {
        super(s);
    }
}
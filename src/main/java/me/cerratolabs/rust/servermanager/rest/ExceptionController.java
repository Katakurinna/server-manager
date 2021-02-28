package me.cerratolabs.rust.servermanager.rest;

import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import me.cerratolabs.rust.servermanager.rest.exceptions.ServerAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String adviceController(InvalidDataException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String adviceController(ServerAlreadyExistException exception) {
        return exception.getMessage();
    }

}
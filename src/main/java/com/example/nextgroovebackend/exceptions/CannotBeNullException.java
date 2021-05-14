package com.example.nextgroovebackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CannotBeNullException extends RuntimeException{
    public CannotBeNullException(String message) {
        super(message);
    }
}

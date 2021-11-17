package com.Oydin.Userservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotFoundException extends RuntimeException {
    private String message;
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public UserNotFoundException() {
    }

}

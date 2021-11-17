package com.Oydin.Userservice.Exception;

public class UserAlreadyExistsException extends RuntimeException{
    private String message;
    private static final Integer serialVersionUID = 1;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
    public UserAlreadyExistsException() {
    }
}

package com.Oydin.Productservice.Exception;

public class ProductAlreadyExistsException extends RuntimeException{
    private String message;
    private static final Integer serialVersionUID = 1;

    public ProductAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
    public ProductAlreadyExistsException() {
    }
}

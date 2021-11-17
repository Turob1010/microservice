package com.Oydin.Productservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ProductNotFoundException extends RuntimeException {
    private String message;
    public ProductNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public ProductNotFoundException() {
    }

}

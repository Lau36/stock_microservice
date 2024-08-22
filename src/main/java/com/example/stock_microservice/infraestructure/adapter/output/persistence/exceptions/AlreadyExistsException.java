package com.example.stock_microservice.infraestructure.adapter.output.persistence.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

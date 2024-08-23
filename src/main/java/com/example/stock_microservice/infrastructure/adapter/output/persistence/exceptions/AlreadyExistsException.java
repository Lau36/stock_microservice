package com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

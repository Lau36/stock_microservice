package com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}

package com.example.stock_microservice.domain.execptions;

public class DuplicateCategoriesException extends RuntimeException {
    public DuplicateCategoriesException(String message) {
        super(message);
    }
}

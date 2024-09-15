package com.example.stock_microservice.domain.execptions;

public class NotNegativeException extends RuntimeException {
    public NotNegativeException(String message) {
        super(message);
    }
}

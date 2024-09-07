package com.example.stock_microservice.domain.execptions;

public class NotNullException extends RuntimeException {
    public NotNullException(String message) {
        super(message);
    }
}

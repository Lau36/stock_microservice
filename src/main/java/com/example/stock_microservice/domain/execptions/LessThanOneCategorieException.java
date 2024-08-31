package com.example.stock_microservice.domain.execptions;

public class LessThanOneCategorieException extends RuntimeException {
    public LessThanOneCategorieException(String message) {
        super(message);
    }
}

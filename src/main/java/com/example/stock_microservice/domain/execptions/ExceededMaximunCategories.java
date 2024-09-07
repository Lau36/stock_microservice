package com.example.stock_microservice.domain.execptions;

public class ExceededMaximunCategories extends RuntimeException {
    public ExceededMaximunCategories(String message) {
        super(message);
    }
}

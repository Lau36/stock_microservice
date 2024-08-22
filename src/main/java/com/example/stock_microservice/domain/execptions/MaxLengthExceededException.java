package com.example.stock_microservice.domain.execptions;

public class MaxLengthExceededException extends RuntimeException {
    public MaxLengthExceededException(String fieldName, int maxLength) {
        super(String.format("The field '%s' exceeded the maximum length of %d", fieldName, maxLength));
    }
}

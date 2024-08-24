package com.example.stock_microservice.infrastructure.adapter.input.exceptions;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message){
        super(message);
    }
}

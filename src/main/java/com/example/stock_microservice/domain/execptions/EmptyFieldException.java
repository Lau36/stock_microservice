package com.example.stock_microservice.domain.execptions;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message){
        super(message);
    }
}

package com.example.stock_microservice.infraestructure.configuration.exceptionhandler;

import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.exceptions.AlreadyExistsException;
import com.example.stock_microservice.infraestructure.configuration.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EMPTY_FIELD_EXCEPTION_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(MaxLengthExceededException.class)
    public ResponseEntity<ExceptionResponse> handleMaxLengthExceededException(MaxLengthExceededException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.MAX_LENGTH_EXCEEDED_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }
}

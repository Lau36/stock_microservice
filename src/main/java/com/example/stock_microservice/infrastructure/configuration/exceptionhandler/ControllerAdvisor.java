package com.example.stock_microservice.infrastructure.configuration.exceptionhandler;

import com.example.stock_microservice.infrastructure.adapter.input.exceptions.EmptyFieldException;
import com.example.stock_microservice.infrastructure.adapter.input.exceptions.MaxLengthExceededException;
import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.infrastructure.configuration.Constants;
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

package com.example.stock_microservice.infrastructure.configuration.exceptionhandler;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.infrastructure.adapter.input.exceptions.MaxLengthExceededException;
import com.example.stock_microservice.infrastructure.configuration.Constants;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.utils.DomainConstants;
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
                String.format(Constants.ALREADY_EXISTS_NAME_EXCEPTION_MESSAGE, e.getMessage()),
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

    @ExceptionHandler(ExceededMaximunCategories.class)
    public ResponseEntity<ExceptionResponse> handleMaximunCategories(ExceededMaximunCategories e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EXCEEDED_MAXIMUM_CATEGORIES, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }
    @ExceptionHandler(NotNullException.class)
    public ResponseEntity<ExceptionResponse> handleNotNullException(NotNullException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.CATEGORIES_NULL_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }
    @ExceptionHandler(DuplicateCategoriesException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicatedCategoriesException(DuplicateCategoriesException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.DUPLICATED_CATEGORIES_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                String.format(Constants.NOT_FOUND_EXCEPTION_MESSAGE, e.getMessage()),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(NotNegativeException.class)
    public ResponseEntity<ExceptionResponse> handleNotNegativeException(NotNegativeException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                DomainConstants.QUANTITY_NOT_NEGATIVE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }


}

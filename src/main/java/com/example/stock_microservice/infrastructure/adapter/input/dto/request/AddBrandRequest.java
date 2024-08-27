package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import com.example.stock_microservice.infrastructure.adapter.input.exceptions.EmptyFieldException;
import com.example.stock_microservice.infrastructure.adapter.input.exceptions.MaxLengthExceededException;
import com.example.stock_microservice.utils.DomainConstants;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class AddBrandRequest {
    private String name;
    private String description;

    public AddBrandRequest( String name, String description) {
        if(name.trim().isEmpty()){
            throw new EmptyFieldException( DomainConstants.Field.NAME.toString());
        }
        if(description.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString() );
        }
        if(name.length() > 50){
            throw new MaxLengthExceededException(DomainConstants.Field.NAME.toString(), 50);
        }
        if(description.length() > 120){
            throw new MaxLengthExceededException(DomainConstants.Field.DESCRIPTION.toString(), 120);
        }
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }
}

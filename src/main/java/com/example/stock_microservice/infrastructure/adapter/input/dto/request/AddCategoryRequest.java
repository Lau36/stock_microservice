package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import com.example.stock_microservice.utils.DomainConstants;
import com.example.stock_microservice.infrastructure.adapter.input.exceptions.EmptyFieldException;
import com.example.stock_microservice.infrastructure.adapter.input.exceptions.MaxLengthExceededException;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class AddCategoryRequest {
    private String categoryName;
    private String categoryDescription;

    public AddCategoryRequest( String categoryName, String categoryDescription) {
        if(categoryName.trim().isEmpty()){
            throw new EmptyFieldException( DomainConstants.Field.NAME.toString());
        }
        if(categoryDescription.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString() );
        }
        if(categoryName.length() > 50){
            throw new MaxLengthExceededException(DomainConstants.Field.NAME.toString(), 50);
        }
        if(categoryDescription.length() > 90){
            throw new MaxLengthExceededException(DomainConstants.Field.DESCRIPTION.toString(), 90);
        }
        this.categoryName = requireNonNull(categoryName, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.categoryDescription = requireNonNull(categoryDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }
}

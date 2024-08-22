package com.example.stock_microservice.domain.models;

import com.example.stock_microservice.domain.Utils.DomainConstants;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;

import static java.util.Objects.requireNonNull;

public class Category {
    private final Long id;
    private String categoryName;
    private String categoryDescription;

    public Category(Long id, String categoryName, String categoryDescription) {
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
        this.id = id;
        this.categoryName = requireNonNull(categoryName, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.categoryDescription = requireNonNull(categoryDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

}

package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import com.example.stock_microservice.utils.DomainConstants;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class AddCategoryRequest {
    private String categoryName;
    private String categoryDescription;

    public AddCategoryRequest( String categoryName, String categoryDescription) {
        this.categoryName = requireNonNull(categoryName, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.categoryDescription = requireNonNull(categoryDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }
}

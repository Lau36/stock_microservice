package com.example.stock_microservice.domain.models;

import com.example.stock_microservice.utils.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Category {
    private final Long id;
    private String categoryName;
    private String categoryDescription;

    public Category(Long id, String categoryName, String categoryDescription) {
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

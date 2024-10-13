package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

public class CategoryDTO {
    private String categoryName;
    private String categoryDescription;

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}

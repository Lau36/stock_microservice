package com.example.stock_microservice.domain.dto;

import java.util.List;

public class ListCategoriesDTO {
    List<Long> categories;

    public ListCategoriesDTO(List<Long> categories) {
        this.categories = categories;
    }

    public List<Long> getCategories() {
        return categories;
    }
}

package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import java.util.List;

public class ListCategoriesResponse{
    private List<Long> categoriesId;

    public ListCategoriesResponse(List<Long> categoriesId) {
        categoriesId = categoriesId;
    }

}

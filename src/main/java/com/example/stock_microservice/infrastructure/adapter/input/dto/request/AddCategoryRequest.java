package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {
    private String categoryName;
    private String categoryDescription;
}

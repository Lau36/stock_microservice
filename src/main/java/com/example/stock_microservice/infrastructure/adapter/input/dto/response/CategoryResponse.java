package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {
    private final Long id;
    private final String categoryName;
    private final String categoryDescription;
}

package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.dto.PaginatedCategories;
import com.example.stock_microservice.domain.dto.PaginationRequest;
import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public interface ICategoryUseCases {
    Category createCategory(Category category);
    List<Category> getAll();
    PaginatedCategories listCategories(PaginationRequest paginationRequest);
}

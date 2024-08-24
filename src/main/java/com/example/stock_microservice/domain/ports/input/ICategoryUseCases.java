package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public interface ICategoryUseCases {
    Category createCategory(Category category);
    List<Category> getAll();
}

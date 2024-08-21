package com.example.stock_microservice.application.ports.input;

import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public interface CategoryServicePort {
    Category createCategory(Category category);
    List<Category> getAll();
}

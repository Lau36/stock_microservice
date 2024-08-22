package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.ports.input.CategoryUseCases;
import com.example.stock_microservice.domain.ports.output.CategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public class CategoryService implements CategoryUseCases {

    private final CategoryUseCases categoryUseCases;

    public CategoryService(CategoryUseCases categoryUseCases) {
        this.categoryUseCases = categoryUseCases;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryUseCases.createCategory(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryUseCases.getAll();
    }
}

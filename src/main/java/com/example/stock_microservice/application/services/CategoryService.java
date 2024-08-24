package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.ports.input.ICategoryUseCases;
import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public class CategoryService implements ICategoryUseCases {

    private final ICategoryUseCases categoryUseCases;

    public CategoryService(ICategoryUseCases categoryUseCases) {
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

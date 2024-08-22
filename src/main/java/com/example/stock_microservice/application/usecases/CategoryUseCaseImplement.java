package com.example.stock_microservice.application.usecases;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.CategoryUseCases;
import com.example.stock_microservice.domain.ports.output.CategoryPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryUseCaseImplement implements CategoryUseCases {

    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public Category createCategory(Category category) {
        return categoryPersistencePort.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryPersistencePort.getAll();
    }
}

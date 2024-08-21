package com.example.stock_microservice.application.services;

import com.example.stock_microservice.application.ports.input.CategoryServicePort;
import com.example.stock_microservice.application.ports.output.CategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//Servicio en donde está la implementación de todo
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServicePort {

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

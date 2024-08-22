package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public interface CategoryPersistencePort {

    Category save(Category category);
    List<Category> getAll();
}

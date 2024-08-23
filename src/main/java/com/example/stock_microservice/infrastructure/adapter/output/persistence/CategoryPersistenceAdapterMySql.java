package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.ports.output.CategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions.AlreadyExistsException;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class CategoryPersistenceAdapterMySql implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        if(categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new AlreadyExistsException("The field '" + category.getCategoryName() + "' already exists");
        }
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategory(savedEntity);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }
}

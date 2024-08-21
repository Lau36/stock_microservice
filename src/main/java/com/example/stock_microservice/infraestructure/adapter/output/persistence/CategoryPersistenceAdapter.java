package com.example.stock_microservice.infraestructure.adapter.output.persistence;

import com.example.stock_microservice.application.ports.output.CategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategory(savedEntity);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }
}

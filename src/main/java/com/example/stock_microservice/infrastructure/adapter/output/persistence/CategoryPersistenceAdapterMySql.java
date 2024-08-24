package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class CategoryPersistenceAdapterMySql implements ICategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategory(savedEntity);
    }

    @Override
    public Optional<Category> findByCategoryName(String categoryName) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByCategoryName(categoryName);
        return categoryEntity.map(categoryMapper::toCategory);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }


}

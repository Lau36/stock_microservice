package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.dto.PaginatedCategories;
import com.example.stock_microservice.domain.dto.PaginationRequest;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public PaginatedCategories listCategories(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()),paginationRequest.getSort());
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable);
        List<Category> categories2 = categoryMapper.toCategoryList(categoryEntities.getContent());
        return new PaginatedCategories(categories2,categoryEntities.getNumber(),categoryEntities.getTotalPages(),categoryEntities.getTotalElements());
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }


}

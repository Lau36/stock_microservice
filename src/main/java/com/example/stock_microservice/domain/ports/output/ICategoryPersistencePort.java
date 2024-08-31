package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.utils.PaginatedCategories;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.models.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {
    Category save(Category category);
    List<Category> getAll();
    Optional<Category> findByCategoryName(String categoryName);
    PaginatedCategories listCategories(PaginationRequest paginationRequest);
    List<Category> findAllById(List<Long> ids);
}

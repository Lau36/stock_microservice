package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.dto.PaginatedCategories;
import com.example.stock_microservice.domain.dto.PaginationRequest;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.ICategoryUseCases;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.execptions.AlreadyExistsException;


import java.util.List;


public class CategoryUseCaseImplement implements ICategoryUseCases {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCaseImplement(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category createCategory(Category category) {
        if(categoryPersistencePort.findByCategoryName(category.getCategoryName()).isPresent()){
            throw new AlreadyExistsException("The field '" + category.getCategoryName() + "' already exists");
        }
        return categoryPersistencePort.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryPersistencePort.getAll();
    }

    @Override
    public PaginatedCategories listCategories(PaginationRequest paginationRequest) {
        return categoryPersistencePort.listCategories(paginationRequest);
    }


}

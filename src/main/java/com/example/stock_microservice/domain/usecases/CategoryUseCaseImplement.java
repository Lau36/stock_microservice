package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.utils.PaginatedCategories;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.ICategoryUseCases;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.utils.DomainConstants;


import java.util.List;


public class CategoryUseCaseImplement implements ICategoryUseCases {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCaseImplement(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category createCategory(Category category) {
        if(category.getCategoryName().trim().isEmpty()){
            throw new EmptyFieldException( DomainConstants.Field.NOMBRE.toString());
        }
        if(category.getCategoryDescription().trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPCION.toString() );
        }
        if(category.getCategoryName().length() > 50){
            throw new MaxLengthExceededException(DomainConstants.Field.NOMBRE.toString(), 50);
        }
        if(category.getCategoryDescription().length() > 90){
            throw new MaxLengthExceededException(DomainConstants.Field.DESCRIPCION.toString(), 90);
        }

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

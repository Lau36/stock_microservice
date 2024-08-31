package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.utils.DomainConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemUseCaseImpl implements IItemUseCase {

    private final IItemPersistencePort articlePersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ItemUseCaseImpl(IItemPersistencePort articlePersistencePort, IBrandPersistencePort brandPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Item createItem(Item item) {

        if(item.getIdCategories() == null){
            throw new NotNullException(DomainConstants.Field.CATEGORIAS.toString());
        }
        if (item.getIdCategories().size() > 3){
            throw new ExceededMaximunCategories(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE);
        }
        if(item.getIdCategories().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.CATEGORIAS.toString());
        }
        Set<Long> categoryIds = new HashSet<>(item.getIdCategories());
        if(categoryIds.size() < item.getIdCategories().size()){
            throw new DuplicateCategoriesException(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION);
        }
        if(item.getIdBrand() == null){
            throw new NotNullException(DomainConstants.Field.ID_BRAND.toString());
        }
        if(articlePersistencePort.findByName(item.getName()).isPresent()){
            throw new AlreadyExistsException(item.getName());
        }
        if(brandPersistencePort.findById(item.getIdBrand()).isEmpty()){
            throw new NotFoundException("La marca'"+item.getIdBrand().toString()+ "'no existe en la base de datos");
        }
        List<Category> categories = categoryPersistencePort.findAllById(item.getIdCategories());
        if(categories.size() < item.getIdCategories().size()){
            throw new NotFoundException("Alguna de estas categorias'"+ item.getIdCategories().toString()+"'no existen en la base de datos");
        }
        return articlePersistencePort.saveArticle(item);
    }
}

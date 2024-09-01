package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.utils.DomainConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemUseCaseImpl implements IItemUseCase {

    private final IItemPersistencePort itemPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ItemUseCaseImpl(IItemPersistencePort articlePersistencePort, IBrandPersistencePort brandPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.itemPersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Item createItem(Item item) {

        if(item.getCategories() == null){
            throw new NotNullException(DomainConstants.Field.CATEGORIAS.toString());
        }
        if (item.getCategories().size() > 3){
            throw new ExceededMaximunCategories(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE);
        }
        if(item.getCategories().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.CATEGORIAS.toString());
        }
        Set<Category> categorySet = new HashSet<>(item.getCategories());
        if(categorySet.size() < item.getCategories().size()){
            throw new DuplicateCategoriesException(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION);
        }
        if(item.getBrand() == null){
            throw new NotNullException(DomainConstants.Field.ID_BRAND.toString());
        }
        if(itemPersistencePort.findByName(item.getName()).isPresent()){
            throw new AlreadyExistsException(item.getName());
        }
        if(brandPersistencePort.findById(item.getBrand().getId()).isEmpty()){
            throw new NotFoundException("La marca'"+item.getBrand().toString()+ "'no existe en la base de datos");
        }
        List<Long> categoryIds = item.getCategories().stream().map(Category::getId).toList();
        List<Category> categories = categoryPersistencePort.findAllById(categoryIds);
        if(categories.size() < item.getCategories().size()){
            throw new NotFoundException("Alguna de estas categorias'"+ item.getCategories().toString()+"'no existen en la base de datos");
        }
        return itemPersistencePort.saveArticle(item);
    }

    @Override
    public Paginated<Item> getItems(PaginationRequest paginationRequest) {
        return itemPersistencePort.listAllItemsPaginated(paginationRequest);
    }

}

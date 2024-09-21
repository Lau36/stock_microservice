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

        if(item.getBrand() == null){
            throw new NotNullException(DomainConstants.Field.BRAND.toString());
        }

        List<Long> categoriesIds = item.getCategories().stream().map(Category::getId).toList();
        Set<Long> categoryIdsSet = new HashSet<>(categoriesIds);
        if(categoryIdsSet.size() < categoriesIds.size()){
            throw new DuplicateCategoriesException(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION);
        }

        if (item.getCategories().size() > 3){
            throw new ExceededMaximunCategories(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE);
        }
        if(item.getCategories().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.CATEGORIAS.toString());
        }

        if(itemPersistencePort.findByName(item.getName()).isPresent()){
            throw new AlreadyExistsException(item.getName());
        }
        if(brandPersistencePort.findById(item.getBrand().getId()).isEmpty()){
            throw new NotFoundException(DomainConstants.BRAND_NOT_FOUND);
        }
        List<Long> categoryIds = item.getCategories().stream().map(Category::getId).toList();
        List<Category> categories = categoryPersistencePort.findAllById(categoryIds);
        if(categories.size() < item.getCategories().size()){
            throw new NotFoundException(DomainConstants.CATEGORIES_NOT_FOUND);
        }

        return itemPersistencePort.saveArticle(item);
    }

    @Override
    public Paginated<Item> getItems(PaginationRequest paginationRequest) {
        return itemPersistencePort.listAllItemsPaginated(paginationRequest);
    }

    @Override
    public Item addStock(Long id, Integer quantity) {
        if(quantity == null || id == null){
            throw new NotNullException(DomainConstants.FIELD_NOT_NULL);
        }
        if(quantity < 0){
            throw new NotNegativeException(DomainConstants.QUANTITY_NOT_NEGATIVE);
        }
        itemExist(id);
        return itemPersistencePort.addStock(id, quantity);
    }

    @Override
    public Boolean isInStock(Long id, Integer quantity) {
        Item item =  itemPersistencePort.findById(id).orElseThrow(
                () -> new NotFoundException(DomainConstants.ITEM_NOT_FOUND)
        );
        return item.getAmount() >= quantity;
    }

    @Override
    public List<Long> getAllCategoriesByItemId(Long id) {
        itemExist(id);
        return itemPersistencePort.getAllCategoriesByItemId(id);
    }

    private void itemExist(Long id){
        if(itemPersistencePort.findById(id).isEmpty()){
            throw new NotFoundException(DomainConstants.ITEM_NOT_FOUND);
        }
    }

}

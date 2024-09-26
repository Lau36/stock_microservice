package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.PaginationRequestItems;

import java.util.List;

public class ItemService implements IItemUseCase {
    private final IItemUseCase itemUseCase;

    public ItemService(IItemUseCase articleUseCase) {
        this.itemUseCase = articleUseCase;
    }

    @Override
    public Item createItem(Item item) {
        return itemUseCase.createItem(item);
    }

    @Override
    public Paginated<Item> getItems(PaginationRequest paginationRequest) {
        return itemUseCase.getItems(paginationRequest);
    }

    @Override
    public Item addStock(Long id, Integer quantity) {
        return itemUseCase.addStock(id, quantity);
    }

    @Override
    public Boolean isInStock(Long id, Integer quantity) {
        return itemUseCase.isInStock(id, quantity);
    }

    @Override
    public List<Long> getAllCategoriesByItemId(Long id) {
        return itemUseCase.getAllCategoriesByItemId(id);
    }

    @Override
    public Paginated<Item> getItemsPaginated(PaginationRequestItems paginationRequestItems) {
        return itemUseCase.getItemsPaginated(paginationRequestItems);
    }


    @Override
    public List<Item> getItemsWithPrice(List<Long> ids) {
        return itemUseCase.getItemsWithPrice(ids);
    }


}

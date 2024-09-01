package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;

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
}

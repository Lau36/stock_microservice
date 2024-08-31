package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;

public class ItemService implements IItemUseCase {
    private final IItemUseCase articleUseCase;

    public ItemService(IItemUseCase articleUseCase) {
        this.articleUseCase = articleUseCase;
    }

    @Override
    public Item createItem(Item item) {
        return articleUseCase.createItem(item);
    }
}

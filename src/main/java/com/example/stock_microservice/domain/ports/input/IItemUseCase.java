package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Item;

public interface IItemUseCase {
    Item createItem(Item item);
}

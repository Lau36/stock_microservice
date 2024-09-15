package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;

public interface IItemUseCase {
    Item createItem(Item item);
    Paginated<Item> getItems(PaginationRequest paginationRequest);
    Item addStock(Long id, Integer quantity);
}

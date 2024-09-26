package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.PaginationRequestItems;

import java.util.List;

public interface IItemUseCase {
    Item createItem(Item item);
    Paginated<Item> getItems(PaginationRequest paginationRequest);
    Item addStock(Long id, Integer quantity);
    Boolean isInStock(Long id, Integer quantity);
    List<Long> getAllCategoriesByItemId(Long id);
    Paginated<Item> getItemsPaginated(PaginationRequestItems paginationRequestItems);
    List<Item> getItemsWithPrice(List<Long> ids);
}

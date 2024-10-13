package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.PaginationRequestItems;

import java.util.List;
import java.util.Optional;

public interface IItemPersistencePort {
    Item saveArticle(Item item);
    Optional<Item> findByName(String articleName);
    Paginated<Item> listAllItemsPaginated(PaginationRequest paginationRequest);
    Item addStock(Long id, Integer quantity);
    Optional<Item> findById(Long id);
    List<Long> getAllCategoriesByItemId(Long id);
    Paginated<Item> getItemsPaginated(PaginationRequestItems paginationRequestItems);
    List<Item> getItemsWithPrice(List<Long> ids);
}

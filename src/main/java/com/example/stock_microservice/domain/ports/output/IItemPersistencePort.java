package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;

import java.util.Optional;

public interface IItemPersistencePort {
    Item saveArticle(Item item);
    Optional<Item> findByName(String articleName);
    Paginated<Item> listAllItemsPaginated(PaginationRequest paginationRequest);
}

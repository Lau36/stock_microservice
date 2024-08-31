package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Item;
import java.util.Optional;

public interface IItemPersistencePort {
    Item saveArticle(Item item);
    Optional<Item> findByName(String articleName);
}

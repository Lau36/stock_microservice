package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.ItemMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.ItemRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor

public class ItemPersistenceAdapterMySql implements IItemPersistencePort {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public Item saveArticle(Item item) {
        ItemEntity itemEntity = itemMapper.toArticleEntity(item);
        ItemEntity savedItemEntity = itemRepository.save(itemEntity);
        return itemMapper.toArticle(savedItemEntity);
    }

    @Override
    public Optional<Item> findByName(String articleName) {
        Optional<ItemEntity> articleEntity = itemRepository.findByName(articleName);
        return articleEntity.map(itemMapper::toArticle);
    }
}

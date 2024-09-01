package com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;

import java.util.List;

public class ListItemMapper {
    public List<Item> toItems(List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(this::toItem).toList();
    }
    private Item toItem(ItemEntity itemEntity) {

        List<Category> categories = itemEntity.getCategories().stream()
                .map(categoryEntity -> new Category(categoryEntity.getId(), categoryEntity.getCategoryName(),categoryEntity.getCategoryDescription())).toList();

        Brand brand = new Brand(itemEntity.getBrand().getId(), itemEntity.getBrand().getName(), itemEntity.getBrand().getDescription());

        return new Item(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getDescription(),
                itemEntity.getAmount(),
                itemEntity.getPrice(),
                categories,
                brand
        );
    }
}

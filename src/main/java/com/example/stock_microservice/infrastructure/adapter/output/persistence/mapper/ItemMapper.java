package com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions.EntityNotFoundException;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class ItemMapper {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ItemEntity toArticleEntity(Item item) {
        List<CategoryEntity> categoriesEntity = categoryRepository.findAllByIdIn(item.getIdCategories());
        BrandEntity brand = brandRepository.findById(item.getIdBrand()).orElseThrow(()-> new EntityNotFoundException("No se encontró una marca con el id " + item.getIdBrand()));
        return new ItemEntity(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAmount(),
                item.getPrice(),
                categoriesEntity,
                brand
        );
    }

    public Item toArticle(ItemEntity itemEntity){
        List<Long> categoryIds = itemEntity.getCategories().stream().map(CategoryEntity::getId).toList();
        return new Item(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getDescription(),
                itemEntity.getAmount(),
                itemEntity.getPrice(),
                categoryIds,
                itemEntity.getBrand().getId()
        );
    }

//    public List<Item> toItemList(List<ItemEntity> itemsEntity) {
//        return new
//    }
}

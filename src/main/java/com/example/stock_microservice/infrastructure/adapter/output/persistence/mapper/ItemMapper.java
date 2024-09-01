package com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ItemMapper {
    ItemEntity toItemEntity(Item item);
    Item toItem(ItemEntity itemEntity);
}

package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemResponseMapper {
    AddItemResponse toAddItemResponse(String message , Item item);
    AddStockResponse toAddStockResponse(Item item);
    ItemResponseDTO toItemResponseDTO(Item item);
    ItemDTO toItemDTO(Item item);
    List<ItemsWithPrice> toItemsWithPrice(List<Item> items);
}

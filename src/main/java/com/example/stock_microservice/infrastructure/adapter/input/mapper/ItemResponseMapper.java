package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddStockResponse;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.ItemResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemResponseMapper {
    AddItemResponse toAddItemResponse(String message , Item item);
    AddStockResponse toAddStockResponse(Item item);
    ItemResponseDTO toItemResponseDTO(Item item);
}

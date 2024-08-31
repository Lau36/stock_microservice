package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemResponseMapper {
    AddItemResponse toAddItemResponse(String message , Item item);
}

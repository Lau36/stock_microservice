package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {
    Item toArticle(AddItemRequest addItemRequest);
}

package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category addRequestToCategory(AddCategoryRequest addCategoryRequest);
}

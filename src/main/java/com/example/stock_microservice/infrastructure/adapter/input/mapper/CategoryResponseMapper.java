package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

   CategoryResponse fromCategory(Category category);

   List<CategoryResponse> toCategoryResponses(List<Category> categories);
}

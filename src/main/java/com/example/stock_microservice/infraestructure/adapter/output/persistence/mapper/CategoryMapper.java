package com.example.stock_microservice.infraestructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toCategoryEntity(Category category);

    Category toCategory(CategoryEntity categoryEntity);

    List<Category> toCategoryList(List<CategoryEntity> categoryEntities);

}

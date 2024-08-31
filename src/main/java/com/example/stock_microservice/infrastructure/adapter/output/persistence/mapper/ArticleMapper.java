package com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ArticleEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions.EntityNotFoundException;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ArticleMapper {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ArticleEntity toArticleEntity(Article article) {
        List<CategoryEntity> categoriesEntity = categoryRepository.findAllByIdIn(article.getId_categories());
        BrandEntity brand = brandRepository.findById(article.getId_brand()).orElseThrow(()-> new EntityNotFoundException("No se encontró una marca con el id " + article.getId_brand()));
        return new ArticleEntity(
                article.getId(),
                article.getName(),
                article.getDescription(),
                article.getAmount(),
                article.getPrice(),
                categoriesEntity,
                brand
        );
    }

    public Article toArticle(ArticleEntity articleEntity){
        List<Long> categoryIds = articleEntity.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList());
        return new Article(
                articleEntity.getId(),
                articleEntity.getName(),
                articleEntity.getDescription(),
                articleEntity.getAmount(),
                articleEntity.getPrice(),
                categoryIds,
                articleEntity.getBrand().getId()
        );
    }
}

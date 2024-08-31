package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.ArticleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleResponseMapper {
    ArticleResponse toAddArticleResponse(Article article);
}

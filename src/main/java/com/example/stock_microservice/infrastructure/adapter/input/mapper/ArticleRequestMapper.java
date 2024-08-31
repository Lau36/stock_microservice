package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddArticleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleRequestMapper {
    Article toArticle(AddArticleRequest addArticleRequest);
}

package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Article;

public interface IArticleUseCase {
    Article createArticle(Article article);
}

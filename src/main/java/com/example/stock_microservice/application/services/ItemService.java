package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.ports.input.IArticleUseCase;

public class ItemService implements IArticleUseCase {
    private final IArticleUseCase articleUseCase;

    public ItemService(IArticleUseCase articleUseCase) {
        this.articleUseCase = articleUseCase;
    }

    @Override
    public Article createArticle(Article article) {
        return articleUseCase.createArticle(article);
    }
}

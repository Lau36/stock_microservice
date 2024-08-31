package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Article;
import java.util.Optional;

public interface IArticlePersistencePort {
    Article saveArticle(Article article);
    Optional<Article> findByName(String articleName);
}

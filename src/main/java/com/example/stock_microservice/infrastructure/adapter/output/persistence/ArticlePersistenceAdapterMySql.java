package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.ports.output.IArticlePersistencePort;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ArticleEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.ArticleMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.ArticleRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor

public class ArticlePersistenceAdapterMySql implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public Article saveArticle(Article article) {
        ArticleEntity articleEntity = articleMapper.toArticleEntity(article);
        ArticleEntity savedArticleEntity = articleRepository.save(articleEntity);
        return articleMapper.toArticle(savedArticleEntity);
    }

    @Override
    public Optional<Article> findByName(String articleName) {
        Optional<ArticleEntity> articleEntity = articleRepository.findByName(articleName);
        return articleEntity.map(articleMapper::toArticle);
    }
}

package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String articleName);
}

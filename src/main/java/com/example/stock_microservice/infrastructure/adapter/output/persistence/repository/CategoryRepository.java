package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryName(String categoryName);
    List<CategoryEntity> findAllByIdIn(List<Long> ids);
}

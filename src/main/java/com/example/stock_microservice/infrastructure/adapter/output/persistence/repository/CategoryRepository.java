package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCategoryName(String name);
}

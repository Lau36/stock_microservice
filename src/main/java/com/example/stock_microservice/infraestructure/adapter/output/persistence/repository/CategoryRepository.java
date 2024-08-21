package com.example.stock_microservice.infraestructure.adapter.output.persistence.repository;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infraestructure.adapter.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}

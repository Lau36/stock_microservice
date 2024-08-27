package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
}

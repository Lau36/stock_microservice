package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String articleName);
}

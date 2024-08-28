package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Brand;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand save(Brand brand);
    Optional<Brand> findByBrandName(String name);
}

package com.example.stock_microservice.domain.ports.output;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand save(Brand brand);
    Optional<Brand> findByBrandName(String name);
    PaginatedBrands listAllBrands(PaginationRequest paginationRequest);

}

package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;

public class BrandService implements IBrandUseCase {
    private final IBrandUseCase brandUseCase;

    public BrandService(IBrandUseCase brandUseCase) {
        this.brandUseCase = brandUseCase;
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandUseCase.createBrand(brand);
    }
}

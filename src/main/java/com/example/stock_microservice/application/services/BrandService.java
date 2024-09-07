package com.example.stock_microservice.application.services;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;

public class BrandService implements IBrandUseCase {
    private final IBrandUseCase brandUseCase;

    public BrandService(IBrandUseCase brandUseCase) {
        this.brandUseCase = brandUseCase;
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandUseCase.createBrand(brand);
    }

    @Override
    public PaginatedBrands listAllBrands(PaginationRequest paginationRequest) {
        return brandUseCase.listAllBrands(paginationRequest);
    }
}

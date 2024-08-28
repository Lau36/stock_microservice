package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;

public interface IBrandUseCase {
    Brand createBrand(Brand brand);

    PaginatedBrands listAllBrands(PaginationRequest paginationRequest);
}

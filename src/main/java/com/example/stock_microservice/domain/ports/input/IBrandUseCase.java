package com.example.stock_microservice.domain.ports.input;

import com.example.stock_microservice.domain.models.Brand;

public interface IBrandUseCase {
    Brand createBrand(Brand brand);
}

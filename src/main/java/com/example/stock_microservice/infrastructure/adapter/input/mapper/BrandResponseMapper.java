package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;

public interface BrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);
}

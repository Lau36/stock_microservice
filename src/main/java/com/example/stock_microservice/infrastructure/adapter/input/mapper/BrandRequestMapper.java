package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddBrandRequest;

public interface BrandRequestMapper {
    Category addRequestToBrand(AddBrandRequest brand);
}

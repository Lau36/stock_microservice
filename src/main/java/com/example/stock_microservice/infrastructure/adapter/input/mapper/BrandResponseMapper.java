package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface BrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);
}

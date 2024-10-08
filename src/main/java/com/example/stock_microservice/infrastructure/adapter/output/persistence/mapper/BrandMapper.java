package com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandEntity toBrandEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    List<Brand> toBrandList(List<BrandEntity> brandEntityList);
}

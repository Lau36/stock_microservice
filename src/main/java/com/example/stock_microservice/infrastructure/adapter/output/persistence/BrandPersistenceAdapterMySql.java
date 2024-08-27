package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.BrandMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class BrandPersistenceAdapterMySql implements IBrandPersistencePort {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Brand save(Brand brand) {
        BrandEntity entity = brandMapper.toBrandEntity(brand);
        BrandEntity brandSaved = brandRepository.save(entity);
        return brandMapper.toBrand(brandSaved);
    }

    @Override
    public Optional<Brand> findByBrandName(String name) {
        Optional<BrandEntity> brandEntity = brandRepository.findByName(name);
        return brandEntity.map(brandMapper::toBrand);
    }
}

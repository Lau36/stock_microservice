package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.BrandMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
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
    public Optional<Brand> findById(Long id) {
        Optional<BrandEntity> brandEntity = brandRepository.findById(id);
        return brandEntity.map(brandMapper::toBrand);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        Optional<BrandEntity> brandEntity = brandRepository.findByName(name);
        return brandEntity.map(brandMapper::toBrand);
    }

    @Override
    public PaginatedBrands listAllBrands(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()),paginationRequest.getSort());
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);
        Page<BrandEntity> brandEntities = brandRepository.findAll(pageable);
        List<Brand> brandsList = brandMapper.toBrandList(brandEntities.getContent());
        return new PaginatedBrands(brandsList,brandEntities.getNumber(),brandEntities.getTotalPages(),brandEntities.getTotalElements());
    }
}

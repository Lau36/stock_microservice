package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;


public class BrandUseCaseImpl implements IBrandUseCase {

    private IBrandPersistencePort brandPersistencePort;

    public BrandUseCaseImpl(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand createBrand(Brand brand) {
        if(brandPersistencePort.findByBrandName(brand.getName()).isPresent()){
            throw new AlreadyExistsException("The field '" + brand.getName() + "' already exists");
        }
        return brandPersistencePort.save(brand);
    }
}

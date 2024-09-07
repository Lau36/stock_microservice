package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.utils.DomainConstants;


public class BrandUseCaseImpl implements IBrandUseCase {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCaseImpl(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand createBrand(Brand brand) {
        if(brand.getName().trim().isEmpty()){
            throw new EmptyFieldException( DomainConstants.Field.NOMBRE.toString());
        }
        if(brand.getDescription().trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPCION.toString() );
        }
        if(brand.getName().length() > 50){
            throw new MaxLengthExceededException(DomainConstants.Field.NOMBRE.toString(), 50);
        }
        if(brand.getDescription().length() > 120){
            throw new MaxLengthExceededException(DomainConstants.Field.DESCRIPCION.toString(), 120);
        }
        if(brandPersistencePort.findByName(brand.getName()).isPresent()){
            throw new AlreadyExistsException("The field '" + brand.getName() + "' already exists");
        }
        return brandPersistencePort.save(brand);
    }

    @Override
    public PaginatedBrands listAllBrands(PaginationRequest paginationRequest) {
        return brandPersistencePort.listAllBrands(paginationRequest);
    }

}

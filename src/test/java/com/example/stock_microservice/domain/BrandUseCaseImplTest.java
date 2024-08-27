package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.usecases.BrandUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BrandUseCaseImplTest {
    @Mock
    IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    BrandUseCaseImpl brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        brand = new Brand(1L, "BrandTest1", "Brand test1");
    }

    @Test
    void testCreateBrandSuccess() {
        when(brandPersistencePort.findByBrandName(brand.getName())).thenReturn(Optional.empty());
        when(brandPersistencePort.save(brand)).thenReturn(brand);

        Brand createdBrand = brandUseCase.createBrand(brand);

        assertEquals(createdBrand, brand);
        verify(brandPersistencePort, Mockito.times(1)).findByBrandName(brand.getName());
        verify(brandPersistencePort, Mockito.times(1)).save(brand);
    }

    @Test
    void testCreateBrandAlreadyExists() {
    when(brandPersistencePort.findByBrandName(brand.getName())).thenReturn(Optional.of(brand));
    assertThrows(AlreadyExistsException.class, () -> brandUseCase.createBrand(brand));

    verify(brandPersistencePort, Mockito.times(1)).findByBrandName(brand.getName());
    verify(brandPersistencePort, never()).save(brand);
    }
}

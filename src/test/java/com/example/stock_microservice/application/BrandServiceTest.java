package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BrandServiceTest {
    @Mock
    IBrandUseCase brandUseCase;

    @InjectMocks
    BrandService brandService;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brand = new Brand(1L, "BrandTest1", "Brand test1");
    }

    @Test
    void createBrandTest(){
        when(brandService.createBrand(brand)).thenReturn(brand);

        Brand createdBrand = brandService.createBrand(brand);

        assertEquals(brand, createdBrand);
        verify(brandUseCase, Mockito.times(1)).createBrand(brand);
    }
}

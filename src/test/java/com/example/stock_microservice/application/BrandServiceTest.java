package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.input.IBrandUseCase;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    void testListAllBrands(){
        PaginationRequest paginationRequest = new PaginationRequest(0, 10, "name", SortDirection.ASC);
        PaginatedBrands paginatedBrands = new PaginatedBrands(Arrays.asList(brand,brand),0,4,2);
        when(brandUseCase.listAllBrands(paginationRequest)).thenReturn(paginatedBrands);

        PaginatedBrands result = brandService.listAllBrands(paginationRequest);
        assertEquals(paginatedBrands, result);
        verify(brandUseCase, times(1)).listAllBrands(paginationRequest);
    }
}

package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddBrandRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandControllerTest {
    @Mock
    BrandService brandService;

    @Mock
    BrandRequestMapper brandRequestMapper;

    @Mock
    BrandResponseMapper brandResponseMapper;

    @InjectMocks
    BrandController brandController;

    private AddBrandRequest addBrandRequest;
    private Brand brand;
    private BrandResponse brandResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        addBrandRequest = new AddBrandRequest("brandTest1", "Brand test 1");
        brand = new Brand(1L,"brandTest1", "Brand test 1");
        brandResponse = new BrandResponse("brandTest1", "Brand test 1");
    }

    @Test
    void testCreateBrand() {

        when(brandRequestMapper.addRequestToBrand(addBrandRequest)).thenReturn(brand);
        when(brandService.createBrand(brand)).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);

        ResponseEntity<BrandResponse> response = brandController.createBrand(addBrandRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandResponse, response.getBody());

        verify(brandRequestMapper, Mockito.times(1)).addRequestToBrand(addBrandRequest);
        verify(brandService, Mockito.times(1)).createBrand(brand);
        verify(brandResponseMapper, Mockito.times(1)).toBrandResponse(brand);


    }
}

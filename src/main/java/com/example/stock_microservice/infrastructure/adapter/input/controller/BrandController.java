package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddBrandRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody AddBrandRequest addBrandRequest) {
        Brand createdBrand = brandService.createBrand(brandRequestMapper.addRequestToBrand(addBrandRequest));
        BrandResponse brandResponse = brandResponseMapper.toBrandResponse(createdBrand);
        return ResponseEntity.ok(brandResponse);
    }
}

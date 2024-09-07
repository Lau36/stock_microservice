package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddBrandRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandResponseMapper;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public ResponseEntity<PaginatedBrands> getAllBrandsPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                                         @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                                         @RequestParam(defaultValue = "name") String sort,
                                                                         @RequestParam(defaultValue = "asc")  String sortDirection){
        PaginationRequest paginationRequest = new PaginationRequest(page,size,sort, SortDirection.valueOf(sortDirection.toUpperCase()));
        PaginatedBrands brandList = brandService.listAllBrands(paginationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(brandList);
    }
}

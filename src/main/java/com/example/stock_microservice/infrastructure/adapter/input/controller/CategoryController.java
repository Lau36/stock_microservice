package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.dto.PaginatedCategories;
import com.example.stock_microservice.domain.dto.PaginationRequest;
import com.example.stock_microservice.domain.dto.SortDirection;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddCategoryRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.CategoryResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryResponseMapper;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody AddCategoryRequest addCategoryRequest){
        Category createdCategory = categoryService.createCategory(categoryRequestMapper.addRequestToCategory(addCategoryRequest));
        CategoryResponse createdCategoryResponse = categoryResponseMapper.fromCategory(createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryResponse);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<Category> listOfCategories = categoryService.getAll();
        List<CategoryResponse> listOfCategoriesResponse = categoryResponseMapper.toCategoryResponses(listOfCategories);
        return ResponseEntity.status(HttpStatus.OK).body(listOfCategoriesResponse);
    }

    @GetMapping("/")
    public ResponseEntity<PaginatedCategories> getAllCategoriesPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                                         @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                                         @RequestParam(defaultValue = "categoryName") String sort,
                                                                         @RequestParam(defaultValue = "asc")  String sortDirection){
        PaginationRequest paginationRequest = new PaginationRequest(page,size,sort,SortDirection.valueOf(sortDirection.toUpperCase()));
        PaginatedCategories categories = categoryService.listCategories(paginationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}

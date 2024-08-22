package com.example.stock_microservice.infraestructure.adapter.input.controller;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.models.Category;
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

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> ListOfCategories = categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ListOfCategories);
    }
}

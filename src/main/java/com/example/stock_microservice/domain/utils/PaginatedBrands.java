package com.example.stock_microservice.domain.utils;

import com.example.stock_microservice.domain.models.Brand;

import java.util.List;

public class PaginatedBrands {
    private List<Brand> brands;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PaginatedBrands(List<Brand> brands, int currentPage, int totalPages, long totalElements) {
        this.brands = brands;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
}

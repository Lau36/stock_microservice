package com.example.stock_microservice.domain.utils;

import com.example.stock_microservice.domain.models.Category;

import java.util.List;

public class PaginatedCategories {
    private List<Category> categories;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PaginatedCategories(List<Category> categories, int currentPage, int totalPages, long totalElements) {
        this.categories = categories;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<Category> getCategories() {
        return categories;
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

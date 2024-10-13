package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}

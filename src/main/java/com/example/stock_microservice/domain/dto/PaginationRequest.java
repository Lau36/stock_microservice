package com.example.stock_microservice.domain.dto;


public class PaginationRequest {
    private int page;
    private int size;
    private String sort;
    private SortDirection sortDirection;

    public PaginationRequest(int page, int size, String sort, SortDirection sortDirection) {
        this.page = page;
        this.size = size;
        this.sort =sort;
        this.sortDirection = sortDirection;
    }

    public String getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }
}

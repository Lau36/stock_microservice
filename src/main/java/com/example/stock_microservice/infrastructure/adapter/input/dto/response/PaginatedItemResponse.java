package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedItemResponse{
    private List<ItemResponseDTO> items;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}

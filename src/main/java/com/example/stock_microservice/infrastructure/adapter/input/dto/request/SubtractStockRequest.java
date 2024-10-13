package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubtractStockRequest {
    private Integer itemId;
    private Integer quantity;
}

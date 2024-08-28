package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandResponse {
    private String name;
    private String description;
}

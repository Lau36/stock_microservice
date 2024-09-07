package com.example.stock_microservice.infrastructure.adapter.output.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryNameId {
    private Long id;
    private String name;
}

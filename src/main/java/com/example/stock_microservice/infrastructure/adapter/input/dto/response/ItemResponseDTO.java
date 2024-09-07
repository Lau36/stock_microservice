package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private BigDecimal price;
    private List<NameIdDTO> categories;
    private NameIdDTO brand;
}

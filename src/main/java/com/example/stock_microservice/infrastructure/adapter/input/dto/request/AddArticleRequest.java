package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private final String name;
    private final String description;
    private final Integer amount;
    private final BigDecimal price;
    private final List<Long> id_categories;
    private final Long id_brand;
}

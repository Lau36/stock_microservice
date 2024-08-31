package com.example.stock_microservice.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class Article {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer amount;
    private final BigDecimal price;
    private final List<Long> id_categories;
    private final Long id_brand;

    public Article(Long id, String name, String description, Integer amount, BigDecimal price, List<Long> id_categories, Long id_brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.id_categories = id_categories;
        this.id_brand = id_brand;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }


    public List<Long> getId_categories() {
        return id_categories;
    }

    public Long getId_brand() {
        return id_brand;
    }
}


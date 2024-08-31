package com.example.stock_microservice.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class Item {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer amount;
    private final BigDecimal price;
    private final List<Long> idCategories;
    private final Long idBrand;

    public Item(Long id, String name, String description, Integer amount, BigDecimal price, List<Long> idCategories, Long idBrand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.idCategories = idCategories;
        this.idBrand = idBrand;
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


    public List<Long> getIdCategories() {
        return idCategories;
    }

    public Long getIdBrand() {
        return idBrand;
    }
}


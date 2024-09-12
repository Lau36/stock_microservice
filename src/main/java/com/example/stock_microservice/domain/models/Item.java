package com.example.stock_microservice.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class Item {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer amount;
    private final BigDecimal price;
    private final List<Category> categories;
    private final Brand brand;

    public Item(Long id, String name, String description, Integer amount, BigDecimal price, List<Category> categories, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
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

    public List<Category> getCategories() {
        return categories;
    }

    public Brand getBrand() {
        return brand;
    }
}


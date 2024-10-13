package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

import java.util.List;

public class ItemDTO {
    private String name;
    private String description;
    private int amount;
    private double price;
    private List<CategoryDTO> categories;
    private BrandDTO brand;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public BrandDTO getBrand() {
        return brand;
    }
    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }
}

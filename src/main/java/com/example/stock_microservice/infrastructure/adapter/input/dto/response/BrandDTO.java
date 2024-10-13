package com.example.stock_microservice.infrastructure.adapter.input.dto.response;

public class BrandDTO {
    private String name;
    private String description;


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
}

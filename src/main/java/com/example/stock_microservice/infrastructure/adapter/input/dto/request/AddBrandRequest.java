package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AddBrandRequest {

    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot be longer than 50 characters")
    private String name;

    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 120, message = "Name cannot be longer than 50 characters")
    private String description;
}

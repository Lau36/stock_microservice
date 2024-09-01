package com.example.stock_microservice.infrastructure.adapter.input.mapper;

import com.example.stock_microservice.domain.execptions.NotFoundException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AddItemMapper {
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public Item toItem(AddItemRequest addItemRequest) {
        // Obtener las categorías por sus IDs
        List<Category> categories = categoryPersistencePort.findAllById(addItemRequest.getIdCategories());

        // Obtener la marca por su ID
        Brand brand = brandPersistencePort.findById(addItemRequest.getIdBrand()).orElseThrow(() -> new NotFoundException("La marca con ID '" + addItemRequest.getIdBrand() + "' no existe en la base de datos"));

        // Mapear y devolver el objeto Item
        return new Item(
                null,
                addItemRequest.getName(),
                addItemRequest.getDescription(),
                addItemRequest.getAmount(),
                addItemRequest.getPrice(),
                categories,
                brand
        );
    }
}

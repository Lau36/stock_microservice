package com.example.stock_microservice.infrastructure.adapter.input.controller;


import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemResponseMapper;
import com.example.stock_microservice.utils.DomainConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final AddItemMapper addItemMapper;
    private final ItemResponseMapper itemResponseMapper;

    @PostMapping
    public ResponseEntity<AddItemResponse> createItem(@RequestBody AddItemRequest addItemRequest) {
        Item createdItem = itemService.createItem(addItemMapper.toItem(addItemRequest));
        AddItemResponse response = itemResponseMapper.toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,createdItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

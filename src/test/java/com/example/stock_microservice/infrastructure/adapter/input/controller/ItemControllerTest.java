package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemResponseMapper;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemControllerTest {
    @Mock
    ItemService itemService;

    @Mock
    ItemRequestMapper itemRequestMapper;

    @Mock
    ItemResponseMapper itemResponseMapper;

    @InjectMocks
    ItemController itemController;

    private AddItemRequest addItemRequest;
    private Item item;
    private AddItemResponse addItemResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        addItemRequest = new AddItemRequest("Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 2L);
        item = new Item(1L,"Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 1L);
        addItemResponse = new AddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,"Item", "Description");

    }

    @Test
    void testCreateItem() {
        when(itemRequestMapper.toArticle(addItemRequest)).thenReturn(item);
        when(itemService.createItem(item)).thenReturn(item);
        when(itemResponseMapper.toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,item)).thenReturn(addItemResponse);

        ResponseEntity<AddItemResponse> responseEntity = itemController.createItem(addItemRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(addItemResponse, responseEntity.getBody());

        verify(itemRequestMapper, times(1)).toArticle(addItemRequest);
        verify(itemService, times(1)).createItem(item);
        verify(itemResponseMapper, times(1)).toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,item);
    }
}

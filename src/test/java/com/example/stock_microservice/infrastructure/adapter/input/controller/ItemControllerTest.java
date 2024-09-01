package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
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
    AddItemMapper addItemMapper;

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

        Category category1 = new Category(1L, "nombreTest1", "DescripcionTest1");
        Category category2 = new Category(2L, "nombreTest2", "DescripcionTest2");
        Brand brand = new Brand(1L, "nombreTest1", "DescripcionTest1");
        List<Category> categories = Arrays.asList(category1,category2);
        List<Long> categoriesIds = Arrays.asList(category1.getId(), category2.getId());
        addItemRequest = new AddItemRequest("Item", "Description", 10, new BigDecimal("19.99"), categoriesIds, brand.getId());
        item = new Item(1L,"Item", "Description", 10, new BigDecimal("19.99"), categories, brand);
        addItemResponse = new AddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,"Item", "Description");

    }

    @Test
    void testCreateItem() {
        when(addItemMapper.toItem(addItemRequest)).thenReturn(item);
        when(itemService.createItem(item)).thenReturn(item);
        when(itemResponseMapper.toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,item)).thenReturn(addItemResponse);

        ResponseEntity<AddItemResponse> responseEntity = itemController.createItem(addItemRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(addItemResponse, responseEntity.getBody());

        verify(addItemMapper, times(1)).toItem(addItemRequest);
        verify(itemService, times(1)).createItem(item);
        verify(itemResponseMapper, times(1)).toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,item);
    }
}

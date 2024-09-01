package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {
    @Mock
    private IItemUseCase articleUseCase;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Category category1 = new Category(1L, "nombreTest1", "DescripcionTest1");
        Category category2 = new Category(2L, "nombreTest2", "DescripcionTest2");
        Brand brand = new Brand(1L, "nombreTest1", "DescripcionTest1");
        List<Category> categories = Arrays.asList(category1,category2);
        item = new Item(
                1L,
                "Test Item",
                "This is a test description",
                10,
                new BigDecimal("19.99"),
                categories,
                brand
        );
    }

    @Test
    void testCreateItem() {
        when(articleUseCase.createItem(any(Item.class))).thenReturn(item);

        Item createdItem = itemService.createItem(item);

        verify(articleUseCase).createItem(item);

        assertEquals(item, createdItem);
    }
}

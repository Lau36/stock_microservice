package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.input.IItemUseCase;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
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
import static org.mockito.Mockito.*;

class ItemServiceTest {
    @Mock
    private IItemUseCase itemUseCase;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private PaginationRequest paginationRequest;
    private Paginated<Item> paginatedItems;

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
        paginationRequest = new PaginationRequest(0, 10, "name", SortDirection.ASC);

        Item item1 = new Item(1L, "Item1", "Description1", 10, BigDecimal.valueOf(19.99), List.of(), null);
        Item item2 = new Item(2L, "Item2", "Description2", 20, BigDecimal.valueOf(29.99), List.of(), null);
        paginatedItems = new Paginated<>(List.of(item1, item2), 0, 1, 2);
    }

    @Test
    void testCreateItem() {
        when(itemUseCase.createItem(any(Item.class))).thenReturn(item);

        Item createdItem = itemService.createItem(item);

        verify(itemUseCase).createItem(item);

        assertEquals(item, createdItem);
    }

    @Test
    void testGetItem(){
        when(itemUseCase.getItems(paginationRequest)).thenReturn(paginatedItems);

        Paginated<Item> result = itemService.getItems(paginationRequest);

        verify(itemUseCase, times(1)).getItems(paginationRequest);
        assertEquals(paginatedItems, result);
    }

    @Test
    void testAddStock(){
        when(itemUseCase.addStock(1L,1)).thenReturn(item);
        Item itemUpdated = itemService.addStock(1L,1);
        assertEquals(item, itemUpdated);
        verify(itemUseCase, times(1)).addStock(1L,1);
    }
}

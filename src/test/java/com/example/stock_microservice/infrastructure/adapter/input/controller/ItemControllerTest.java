package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.domain.utils.*;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddStockRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.ItemsRequest;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemResponseMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.PaginatedItemResponseMapper;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {
    @Mock
    ItemService itemService;

    @Mock
    AddItemMapper addItemMapper;

    @Mock
    ItemResponseMapper itemResponseMapper;

    @Mock
    private PaginatedItemResponseMapper paginatedItemResponseMapper;

    @InjectMocks
    ItemController itemController;

    private MockMvc mockMvc;


    private ObjectMapper objectMapper;

    private AddItemRequest addItemRequest;
    private Item item;
    private AddItemResponse addItemResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();

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
    @Test
    void testGetAllItemsPaginated() {
        PaginationRequest paginationRequest = new PaginationRequest(0, 10, "name", SortDirection.ASC);

        Item item1 = new Item(1L, "Item1", "Description1", 10, new BigDecimal("19.99"), List.of(), null);
        Item item2 = new Item(2L, "Item2", "Description2", 5, new BigDecimal("29.99"), List.of(), null);

        Paginated<Item> paginatedItems = new Paginated<>(Arrays.asList(item1, item2), 0, 1, 2);
        PaginatedItemResponse paginatedItemResponse = new PaginatedItemResponse();

        when(itemService.getItems(any(PaginationRequest.class))).thenReturn(paginatedItems);
        when(paginatedItemResponseMapper.toPaginatedItemResponse(paginatedItems)).thenReturn(paginatedItemResponse);

        ResponseEntity<PaginatedItemResponse> response = itemController.getAllItemsPaginated(0, 10, "name", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paginatedItemResponse, response.getBody());

        ArgumentCaptor<PaginationRequest> paginationRequestCaptor = ArgumentCaptor.forClass(PaginationRequest.class);
        verify(itemService, times(1)).getItems(paginationRequestCaptor.capture());

        PaginationRequest capturedPaginationRequest = paginationRequestCaptor.getValue();
        assertEquals(paginationRequest.getPage(), capturedPaginationRequest.getPage());
        assertEquals(paginationRequest.getSize(), capturedPaginationRequest.getSize());
        assertEquals(paginationRequest.getSort(), capturedPaginationRequest.getSort());
        assertEquals(paginationRequest.getSortDirection(), capturedPaginationRequest.getSortDirection());
    }

    @Test
    void updateItemSuccessTest() throws Exception {
        AddStockRequest addStockRequest = new AddStockRequest(1L, 10);
        AddStockResponse addStockResponse = new AddStockResponse(item.getName(), item.getAmount());

        when(itemService.addStock(addStockRequest.getId(), addStockRequest.getQuantity())).thenReturn(item);

        mockMvc.perform(patch("/Item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"quantity\": \"10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value(addStockResponse.getItemName()))
                .andExpect(jsonPath("$.quantity").value(addStockResponse.getQuantity()))
                .andDo(print());
    }

    @Test
    void testGetCategoriesByItemId() throws Exception {
        Long itemId = 1L;
        List<Long> expectedCategories = List.of(1L, 2L, 3L);

        when(itemService.getAllCategoriesByItemId(itemId)).thenReturn(expectedCategories);

        mockMvc.perform(get("/Item/Categories")
                        .param("itemId", String.valueOf(itemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(1L))
                .andExpect(jsonPath("$[1]").value(2L))
                .andExpect(jsonPath("$[2]").value(3L));

        verify(itemService, times(1)).getAllCategoriesByItemId(itemId);
    }

    @Test
    void testGetAllItemsPaginatedWithFilters() throws Exception {
        int page = 1;
        int size = 10;
        String sortDirection = "ASC";
        String filter = "CATEGORYNAME";
        String filterName = "Electronics";

        List<Integer> itemIds = List.of(1, 2, 3);
        ItemsRequest itemsRequest = new ItemsRequest(itemIds);

        List<ItemResponseDTO> items = List.of(
                new ItemResponseDTO(1L, "Item1", "Description1", 10, new BigDecimal("19.99"), null, null),
                new ItemResponseDTO(2L, "Item2", "Description2", 10, new BigDecimal("19.99"), null, null)
        );

        List<Item> itemsPaginated = List.of(
                new Item(1L, "Item1", "Description1", 10, new BigDecimal("19.99"), null, null),
                new Item(2L, "Item2", "Description2", 10, new BigDecimal("19.99"), null, null)
        );

        Paginated<Item> paginatedItems = new Paginated<>(itemsPaginated, 0, 1, 2);
        PaginatedItemResponse paginatedResponse = new PaginatedItemResponse(items, 0, 1, 2);

        when(itemService.getItemsPaginated(any(PaginationRequestItems.class))).thenReturn(paginatedItems);
        when(paginatedItemResponseMapper.toPaginatedItemResponse(paginatedItems)).thenReturn(paginatedResponse);

        mockMvc.perform(get("/Item/ItemsPaginated/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortDirection", sortDirection)
                        .param("filter", filter)
                        .param("filterName", filterName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].id").value(1L))
                .andExpect(jsonPath("$.items[0].name").value("Item1"))
                .andExpect(jsonPath("$.items[0].price").value(19.99))
                .andExpect(jsonPath("$.items[1].id").value(2L))
                .andExpect(jsonPath("$.items[1].name").value("Item2"))
                .andExpect(jsonPath("$.items[1].price").value(19.99))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2));

        // Verificar que los métodos de itemService y paginatedItemResponseMapper se llamaron correctamente
        verify(itemService, times(1)).getItemsPaginated(any(PaginationRequestItems.class));
        verify(paginatedItemResponseMapper, times(1)).toPaginatedItemResponse(paginatedItems);
    }

    @Test
    void testGetItemsWithPrice() throws Exception {
        List<Integer> itemIds = List.of(1, 2);
        ItemsRequest itemsRequest = new ItemsRequest(itemIds);
        List<Item> items = List.of(new Item(1L, "Item1", "Description1", 10, BigDecimal.TEN, null, null),
                new Item(2L, "Item2", "Description2", 10, BigDecimal.TEN, null, null));
        List<ItemsWithPrice> itemsWithPrices = List.of(new ItemsWithPrice(1L, BigDecimal.TEN), new ItemsWithPrice(2L, BigDecimal.TEN));

        when(itemService.getItemsWithPrice(anyList())).thenReturn(items);
        when(itemResponseMapper.toItemsWithPrice(items)).thenReturn(itemsWithPrices);

        mockMvc.perform(get("/Item/Prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].price").value(BigDecimal.TEN))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].price").value(BigDecimal.TEN));

        verify(itemService, times(1)).getItemsWithPrice(anyList());
        verify(itemResponseMapper, times(1)).toItemsWithPrice(items);
    }



}

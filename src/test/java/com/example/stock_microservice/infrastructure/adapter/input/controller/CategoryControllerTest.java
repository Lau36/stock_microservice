package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.utils.PaginatedCategories;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddCategoryRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.CategoryResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        AddCategoryRequest addCategoryRequest = new AddCategoryRequest("Juguetes niños", "Juguetes para niños de 6 a 12 años");
        Category category = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");

        when(categoryRequestMapper.addRequestToCategory(addCategoryRequest)).thenReturn(category);
        when(categoryService.createCategory(category)).thenReturn(category);
        when(categoryResponseMapper.fromCategory(category)).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> responseEntity = categoryController.createCategory(addCategoryRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(categoryResponse, responseEntity.getBody());

        verify(categoryRequestMapper, Mockito.times(1)).addRequestToCategory(addCategoryRequest);
        verify(categoryService, Mockito.times(1)).createCategory(category);
        verify(categoryResponseMapper, Mockito.times(1)).fromCategory(category);
    }

    @Test
    void testGetAllCategories() {
        Category category1 =  new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        Category category2 = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        List<Category> categoryList = Arrays.asList(category1, category2);

        CategoryResponse categoryResponse1 = new CategoryResponse(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        CategoryResponse categoryResponse2 = new CategoryResponse(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        List<CategoryResponse> categoryResponseList = Arrays.asList(categoryResponse1, categoryResponse2);

        when(categoryService.getAll()).thenReturn(categoryList);
        when(categoryResponseMapper.toCategoryResponses(categoryList)).thenReturn(categoryResponseList);

        ResponseEntity<List<CategoryResponse>> responseEntity = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryResponseList, responseEntity.getBody());

        verify(categoryService, Mockito.times(1)).getAll();
        verify(categoryResponseMapper, Mockito.times(1)).toCategoryResponses(categoryList);
    }

    @Test
    void testGetAllCategoriesPaginated(){
        PaginationRequest paginationRequest = new PaginationRequest(0, 10, "categoryName", SortDirection.ASC);
        Category category1 = new Category(1L, "Juguetes", "Test1");
        Category category2 = new Category(2L, "Belleza", "Test2");
        PaginatedCategories paginatedCategories = new PaginatedCategories(Arrays.asList(category1,category2),0,1,2);
        when(categoryService.listCategories(any(PaginationRequest.class))).thenReturn(paginatedCategories);

        ResponseEntity<PaginatedCategories> response = categoryController.getAllCategoriesPaginated(0,10,"categoryName","asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paginatedCategories, response.getBody());

        //Verifies that the argument passed it´s the correct one
        ArgumentCaptor<PaginationRequest> captor = ArgumentCaptor.forClass((PaginationRequest.class));
        verify(categoryService, Mockito.times(1)).listCategories(captor.capture());
        PaginationRequest capturedRequest = captor.getValue();

        assertEquals(paginationRequest.getPage(), capturedRequest.getPage());
        assertEquals(paginationRequest.getSize(), capturedRequest.getSize());
        assertEquals(paginationRequest.getSort(), capturedRequest.getSort());
        assertEquals(paginationRequest.getSortDirection(), capturedRequest.getSortDirection());
    }

}

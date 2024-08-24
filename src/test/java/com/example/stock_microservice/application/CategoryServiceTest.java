package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.ICategoryUseCases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    @Mock
    ICategoryUseCases categoryUseCases;

    @InjectMocks
    CategoryService categoryService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory(){
        Category category = new Category(1l, "TestCategory", "TestDescription");
        when(categoryUseCases.createCategory(category)).thenReturn(category);

        Category returnedCategory = categoryService.createCategory(category);

        assertEquals(category, returnedCategory);
        verify(categoryUseCases, Mockito.times(1)).createCategory(category);

    }

    @Test
    void testGetAllCategories(){
        Category category1 = new Category(1l, "TestCategory", "TestDescription");
        Category category2 = new Category(2l, "TestCategory2", "TestDescription2");
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryUseCases.getAll()).thenReturn(categories);

        List<Category> returnedCategories = categoryService.getAll();

        assertEquals(categories, returnedCategories);
        verify( categoryUseCases, Mockito.times(1)).getAll();
    }

}

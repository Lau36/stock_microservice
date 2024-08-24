package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.CategoryUseCaseImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

 class CategoryUseCaseImplTest {
    @Mock
    ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    CategoryUseCaseImplement categoryUseCaseImplement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        Category category = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        when(categoryPersistencePort.findByCategoryName(category.getCategoryName())).thenReturn(Optional.empty());
        when(categoryPersistencePort.save(category)).thenReturn(category);

        Category result = categoryUseCaseImplement.createCategory(category);

        assertEquals(category, result);
        verify(categoryPersistencePort, Mockito.times(1)).findByCategoryName(category.getCategoryName());
        verify(categoryPersistencePort, Mockito.times(1)).save(category);
    }

    @Test
    void testCreateCategory_AlreadyExists() {

        Category category = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        when(categoryPersistencePort.findByCategoryName(category.getCategoryName())).thenReturn(Optional.of(category));

        assertThrows(AlreadyExistsException.class, () -> categoryUseCaseImplement.createCategory(category));

        verify(categoryPersistencePort, Mockito.times(1)).findByCategoryName(category.getCategoryName());
        verify(categoryPersistencePort, never()).save(category);

    }

    @Test
    void testGetAllCategories() {
        // Arrange
        Category category1 = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        Category category2 = new Category(1L, "Ferreteria", "Cosas de ferreteria");
        List<Category> categoryList = Arrays.asList(category1, category2);

        when(categoryPersistencePort.getAll()).thenReturn(categoryList);

        // Act
        List<Category> result = categoryUseCaseImplement.getAll();

        // Assert
        assertEquals(categoryList, result);
        verify(categoryPersistencePort, times(1)).getAll();
    }
}

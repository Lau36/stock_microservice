package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryPersistenceAdapterMySqlTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryPersistenceAdapterMySql categoryPersistenceAdapter;

    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
        categoryEntity = new CategoryEntity(1L, "Juguetes niños", "Juguetes para niños de 6 a 12 años");
    }

    @Test
    void testSave() {
        when(categoryMapper.toCategoryEntity(any(Category.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        when(categoryMapper.toCategory(any(CategoryEntity.class))).thenReturn(category);

        Category savedCategory = categoryPersistenceAdapter.save(category);

        verify(categoryMapper).toCategoryEntity(category);
        verify(categoryRepository).save(categoryEntity);
        verify(categoryMapper).toCategory(categoryEntity);

        assertNotNull(savedCategory);
        assertEquals("Juguetes niños", savedCategory.getCategoryName());
        assertEquals("Juguetes para niños de 6 a 12 años", savedCategory.getCategoryDescription());
    }

    @Test
    void testFindByCategoryName() {
        when(categoryRepository.findByCategoryName("Juguetes niños")).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toCategory(any(CategoryEntity.class))).thenReturn(category);

        Optional<Category> foundCategory = categoryPersistenceAdapter.findByCategoryName("Juguetes niños");

        verify(categoryRepository).findByCategoryName("Juguetes niños");
        verify(categoryMapper).toCategory(categoryEntity);

        assertTrue(foundCategory.isPresent());
        assertEquals("Juguetes niños", foundCategory.get().getCategoryName());
    }

    @Test
    void testFindByCategoryName_NotFound() {
        when(categoryRepository.findByCategoryName("NonExisting")).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryPersistenceAdapter.findByCategoryName("NonExisting");

        verify(categoryRepository).findByCategoryName("NonExisting");
        verify(categoryMapper, never()).toCategory(any(CategoryEntity.class));

        assertFalse(foundCategory.isPresent());
    }

    @Test
    void testGetAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity));
        when(categoryMapper.toCategoryList(anyList())).thenReturn(List.of(category));

        List<Category> categories = categoryPersistenceAdapter.getAll();

        verify(categoryRepository).findAll();
        verify(categoryMapper).toCategoryList(List.of(categoryEntity));

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Juguetes niños", categories.get(0).getCategoryName());
    }
}
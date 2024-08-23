package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.exceptions.AlreadyExistsException;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryPersistenceAdapterMySqlTest {
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
        category = new Category(1L, "Electronics", "All electronic items");
        categoryEntity = new CategoryEntity(1L, "Electronics", "All electronic items");
    }

    @Test
    void givenCategory_whenSave_thenReturnSavedCategory() {
        // Configuración de mocks
        when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(false);
        when(categoryMapper.toCategoryEntity(any(Category.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        when(categoryMapper.toCategory(any(CategoryEntity.class))).thenReturn(category);

        // Ejecución
        Category savedCategory = categoryPersistenceAdapter.save(category);

        // Verificación
        verify(categoryRepository).existsByCategoryName(category.getCategoryName());
        verify(categoryRepository).save(any(CategoryEntity.class));
        verify(categoryMapper).toCategoryEntity(category);
        verify(categoryMapper).toCategory(categoryEntity);
        assertEquals(category.getCategoryName(), savedCategory.getCategoryName());
        assertEquals(category.getCategoryDescription(), savedCategory.getCategoryDescription());
    }

    @Test
    void givenCategoryWithExistingName_whenSave_thenThrowAlreadyExistsException() {
        // Configuración de mocks
        when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(true);

        // Verificación y Ejecución
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () ->
                categoryPersistenceAdapter.save(category)
        );

        assertEquals("The field 'Electronics' already exists", exception.getMessage());
        verify(categoryRepository).existsByCategoryName(category.getCategoryName());
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
        verify(categoryMapper, never()).toCategoryEntity(category);
    }

    @Test
    void givenCategoriesInDatabase_whenGetAll_thenReturnCategoryList() {
        // Configuración de mocks
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        List<Category> categories = List.of(category);

        when(categoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryMapper.toCategoryList(categoryEntities)).thenReturn(categories);

        // Ejecución
        List<Category> result = categoryPersistenceAdapter.getAll();

        // Verificación
        verify(categoryRepository).findAll();
        verify(categoryMapper).toCategoryList(categoryEntities);
        assertEquals(1, result.size());
        assertEquals(category.getCategoryName(), result.get(0).getCategoryName());
    }
}

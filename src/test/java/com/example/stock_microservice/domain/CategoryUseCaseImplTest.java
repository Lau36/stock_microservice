package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.domain.utils.PaginatedCategories;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.CategoryUseCaseImplement;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
     void testCreateCategory_shouldThrowEmptyFieldException_whenNameIsEmpty() {
         Category  category1 = new Category(  1L,"", "Category  test1");
         assertThatThrownBy(() -> categoryUseCaseImplement.createCategory(category1))
                 .isInstanceOf(EmptyFieldException.class)
                 .hasMessageContaining(DomainConstants.Field.NOMBRE.toString());

         verify(categoryPersistencePort, never()).save(any(Category.class));
     }
     @Test
     void testCreateCategory_shouldThrowEmptyFieldException_whenDescriptionIsEmpty() {
         Category  category1 = new Category(  1L,"CategoryTest1", "");
         assertThatThrownBy(() -> categoryUseCaseImplement.createCategory(category1))
                 .isInstanceOf(EmptyFieldException.class)
                 .hasMessageContaining(DomainConstants.Field.DESCRIPCION.toString());

         verify(categoryPersistencePort, never()).save(any(Category.class));
     }

     @Test
     void testCreateCategory_shouldThrowMaxLengthExceededException_whenNameExceedsMaxLength(){
         Category  category1 = new Category(  1L,"A very long CategoryName A very long CategoryName A very long CategoryName", "Category  test1");
         assertThatThrownBy(() -> categoryUseCaseImplement.createCategory(category1))
                 .isInstanceOf(MaxLengthExceededException.class)
                 .hasMessageContaining(DomainConstants.Field.NOMBRE.toString());

         verify(categoryPersistencePort, never()).save(category1);
     }

     @Test
     void testCreateCategory_shouldNotThrowException_whenNameIsAtMaxLength(){
         String maxLengthName = "A".repeat(50);
         Category category = new Category(1L, maxLengthName, "Valid description");

         when(categoryPersistencePort.findByCategoryName(maxLengthName)).thenReturn(Optional.empty());
         when(categoryPersistencePort.save(category)).thenReturn(category);

         Category result = categoryUseCaseImplement.createCategory(category);

         assertEquals(category, result);
         verify(categoryPersistencePort, times(1)).save(category);
     }

     @Test
     void testCreateCategory_shouldNotThrowException_whenDescriptionIsAtMaxLength(){
         String maxLengthDescription = "A".repeat(90);
         Category category = new Category(1L, "ValidName", maxLengthDescription);

         when(categoryPersistencePort.findByCategoryName("ValidName")).thenReturn(Optional.empty());
         when(categoryPersistencePort.save(category)).thenReturn(category);

         Category result = categoryUseCaseImplement.createCategory(category);

         assertEquals(category, result);
         verify(categoryPersistencePort, times(1)).save(category);
     }

     @Test
     void testCreateCategory_shouldThrowMaxLengthExceededException_whenDescriptionExceedsMaxLength(){
         Category  category1 = new Category(  1L,"CategoryName", "A very long Category  description A very long Category  description A very long Category  description A very long Category  description A very long Category  description A very long Category  description");
         assertThatThrownBy(() -> categoryUseCaseImplement.createCategory(category1))
                 .isInstanceOf(MaxLengthExceededException.class)
                 .hasMessageContaining(DomainConstants.Field.DESCRIPCION.toString());

         verify(categoryPersistencePort, never()).save(category1);
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


     @Test
     void testListCategories(){
         PaginationRequest paginationRequest = new PaginationRequest(0, 10, "categoryName", SortDirection.ASC);
         Category category1 = new Category(1L, "Juguetes", "Test1");
         Category category2 = new Category(2L, "Belleza", "Test2");
         PaginatedCategories paginatedCategories = new PaginatedCategories(Arrays.asList(category1,category2),0,1,2);

         when(categoryPersistencePort.listCategories(paginationRequest)).thenReturn(paginatedCategories);

         PaginatedCategories result = categoryUseCaseImplement.listCategories(paginationRequest);

         assertEquals(paginatedCategories, result);
         verify(categoryPersistencePort, Mockito.times(1)).listCategories(paginationRequest);
     }
}

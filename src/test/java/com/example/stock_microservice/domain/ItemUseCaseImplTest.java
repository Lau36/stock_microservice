package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.ItemUseCaseImpl;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

 class ItemUseCaseImplTest {
     @Mock
     private IItemPersistencePort articlePersistencePort;

     @Mock
     private IBrandPersistencePort brandPersistencePort;

     @Mock
     private ICategoryPersistencePort categoryPersistencePort;

     @InjectMocks
     private ItemUseCaseImpl articleUseCase;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);
     }

     @Test
     void testCreateArticle_Success() {

         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(
                 null,
                 "New Item",
                 "Description of the new item",
                 10,
                 new BigDecimal("29.99"),
                 categoryIds,
                 100L
         );

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test"), new Category(2L, "Category 2", "Description test")));
         when(articlePersistencePort.saveArticle(item)).thenReturn(item);


         Item createdItem = articleUseCase.createItem(item);


         assertNotNull(createdItem);
         assertEquals("New Item", createdItem.getName());
         verify(articlePersistencePort).saveArticle(item);
     }

     @Test
     void testCreateArticle_ThrowsNotNullExceptionForCategories() {
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), null, 100L);

         NotNullException thrown = assertThrows(NotNullException.class, () -> articleUseCase.createItem(item));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsExceededMaximunCategories() {
         List<Long> categoryIds = Arrays.asList(1L, 2L, 3L, 4L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         ExceededMaximunCategories thrown = assertThrows(ExceededMaximunCategories.class, () -> articleUseCase.createItem(item));
         assertEquals(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE, thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsEmptyFieldExceptionForCategories() {
         List<Long> categoryIds = List.of();
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         EmptyFieldException thrown = assertThrows(EmptyFieldException.class, () -> articleUseCase.createItem(item));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsDuplicateCategoriesException() {
         List<Long> categoryIds = Arrays.asList(1L, 1L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         DuplicateCategoriesException thrown = assertThrows(DuplicateCategoriesException.class, () -> articleUseCase.createItem(item));
         assertEquals(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION, thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotNullExceptionForBrand() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, null);

         NotNullException thrown = assertThrows(NotNullException.class, () -> articleUseCase.createItem(item));
         assertEquals("ID_BRAND", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsAlreadyExistsException() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.of(item));

         AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () -> articleUseCase.createItem(item));
         assertEquals("Item", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotFoundExceptionForBrand() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> articleUseCase.createItem(item));
         assertEquals("La marca'100'no existe en la base de datos", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotFoundExceptionForCategories() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test")));

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> articleUseCase.createItem(item));
         assertEquals("Alguna de estas categorias'"+ item.getIdCategories()+"'no existen en la base de datos", thrown.getMessage());
     }

 }
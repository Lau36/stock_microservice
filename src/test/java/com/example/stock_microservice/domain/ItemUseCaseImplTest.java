package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.ItemUseCaseImpl;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
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
     private IItemPersistencePort itemPersistencePort;

     @Mock
     private IBrandPersistencePort brandPersistencePort;

     @Mock
     private ICategoryPersistencePort categoryPersistencePort;

     @InjectMocks
     private ItemUseCaseImpl itemUseCase;
     
     private Category category1;
     private Category category2;
     private Category category3;
     private Category category4;
     List<Category> categories;
     private Brand brand;
     private PaginationRequest paginationRequest;
     private Paginated<Item> paginatedItems;


     @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);
         category1 = new Category(1L, "nombreTest1", "DescripcionTest1");
         category2 = new Category(2L, "nombreTest2", "DescripcionTest2");
         category3 = new Category(3L, "nombreTest3", "DescripcionTest2");
         category4 = new Category(4L, "nombreTest4", "DescripcionTest2");
         brand = new Brand(1L, "nombreTest1", "DescripcionTest1");
         categories = Arrays.asList(category1,category2);
         // Create a sample pagination request
         paginationRequest = new PaginationRequest(0, 10, "name", SortDirection.ASC);

         // Create a sample paginated response
         Item item1 = new Item(1L, "Item1", "Description1", 10, BigDecimal.valueOf(19.99), List.of(), null);
         Item item2 = new Item(2L, "Item2", "Description2", 20, BigDecimal.valueOf(29.99), List.of(), null);
         paginatedItems = new Paginated<>(List.of(item1, item2), 0, 1, 2);
     }

     @Test
     void testCreateItem_Success() {

         List<Long> categoryIds = Arrays.asList(1L, 2L);

         Item item = new Item(
                 null,
                 "New Item",
                 "Description of the new item",
                 10,
                 new BigDecimal("29.99"),
                 categories,
                 brand
         );

         when(itemPersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test"), new Category(2L, "Category 2", "Description test")));
         when(itemPersistencePort.saveArticle(item)).thenReturn(item);


         Item createdItem = itemUseCase.createItem(item);


         assertNotNull(createdItem);
         assertEquals("New Item", createdItem.getName());
         verify(itemPersistencePort).saveArticle(item);
     }

     @Test
     void testCreateItem_ThrowsNotNullExceptionForCategories() {
         Item item = new Item(1L, "Item", "Description", 10, new BigDecimal("19.99"), null, brand);
         NotNullException thrown = assertThrows(NotNullException.class, () -> itemUseCase.createItem(item));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsExceededMaximunCategories() {
         List<Category> moreThan3Categories = Arrays.asList(category1,category2,category3,category4);
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), moreThan3Categories, brand);

         ExceededMaximunCategories thrown = assertThrows(ExceededMaximunCategories.class, () -> itemUseCase.createItem(item));
         assertEquals(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE, thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsEmptyFieldExceptionForCategories() {
         List<Category> categoriesNull = List.of();
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categoriesNull, brand);
         EmptyFieldException thrown = assertThrows(EmptyFieldException.class, () -> itemUseCase.createItem(item));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsDuplicateCategoriesException() {
         Category category = new Category(1L, "nombreTest1", "DescripcionTest1");
         Item item = new Item(
                 null,
                 "Test Item",
                 "Test Description",
                 10,
                 new BigDecimal("9.99"),
                 List.of(category, category),
                 brand
         );

         DuplicateCategoriesException thrown = assertThrows(DuplicateCategoriesException.class, () -> itemUseCase.createItem(item));
         assertEquals(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION, thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsNotNullExceptionForBrand() {
         Item item = new Item(1L, "Item", "Description", 10, new BigDecimal("19.99"), categories, null);

         NotNullException thrown = assertThrows(NotNullException.class, () -> itemUseCase.createItem(item));
         assertEquals("BRAND", thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsAlreadyExistsException() {
         Item item = new Item(null, "Item", "Description", 10, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findByName(anyString())).thenReturn(Optional.of(item));

         AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () -> itemUseCase.createItem(item));
         assertEquals("Item", thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsNotFoundExceptionForBrand() {
         Item item = new Item(1L, "Item", "Description", 10, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> itemUseCase.createItem(item));
         assertEquals("La marca ingresada no existe en la base de datos", thrown.getMessage());
     }

     @Test
     void testCreateItem_ThrowsNotFoundExceptionForCategories() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Item item = new Item(1L, "Item", "Description", 10, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test")));

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> itemUseCase.createItem(item));
         assertEquals("Alguna de las categorias ingresadas no existe en la base de datos", thrown.getMessage());
     }

     @Test
     void testGetItems() {
         when(itemPersistencePort.listAllItemsPaginated(paginationRequest)).thenReturn(paginatedItems);

         Paginated<Item> result = itemUseCase.getItems(paginationRequest);

         verify(itemPersistencePort, times(1)).listAllItemsPaginated(paginationRequest);
         assertEquals(paginatedItems, result);
     }



 }
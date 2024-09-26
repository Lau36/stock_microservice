package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.ItemUseCaseImpl;
import com.example.stock_microservice.domain.utils.*;
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


     @Test
     void TestAddStockSuccess() {
         Item item = new Item(1L, "Item", "Description", 10, new BigDecimal("19.99"), categories, brand);
         when(itemPersistencePort.findById(1L)).thenReturn(Optional.of(item));
         when(itemPersistencePort.addStock(1L, 5)).thenReturn(item);

         Item result = itemPersistencePort.addStock(1L, 5);

         assertNotNull(result);
         verify(itemPersistencePort, times(1)).addStock(1L, 5);
     }

     @Test
     void TestAddStockNullId() {
         Exception exception = assertThrows(NotNullException.class, () -> itemUseCase.addStock(null, 5));
         assertEquals(DomainConstants.FIELD_NOT_NULL, exception.getMessage());
     }

     @Test
     void testAddStockNullQuantity() {
         Exception exception = assertThrows(NotNullException.class, () -> itemUseCase.addStock(1L, null));
         assertEquals(DomainConstants.FIELD_NOT_NULL, exception.getMessage());
     }

     @Test
     void testAddStockNegativeQuantity() {
         Exception exception = assertThrows(NotNegativeException.class, () -> itemUseCase.addStock(1L, -5));
         assertEquals(DomainConstants.QUANTITY_NOT_NEGATIVE, exception.getMessage());
     }

     @Test
     void testAddStockItemNotFound() {
         when(itemPersistencePort.findById(1L)).thenReturn(Optional.empty());

         Exception exception = assertThrows(NotFoundException.class, () -> itemUseCase.addStock(1L, 5));
         assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());
     }

     @Test
     void testIsInStock_ItemExists_EnoughStock() {
         Long itemId = 1L;
         Integer requestedQuantity = 5;
         Item item = new Item(1L, "Item2", "Description2", 10, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.of(item));

         Boolean isInStock = itemUseCase.isInStock(itemId, requestedQuantity);

         assertTrue(isInStock);

         verify(itemPersistencePort, times(1)).findById(itemId);
     }

     @Test
     void testIsInStock_ItemExists_NotEnoughStock() {
         Long itemId = 1L;
         Integer requestedQuantity = 15;
         Item item = new Item(1L, "Item2", "Description2", 0, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.of(item));

         Boolean isInStock = itemUseCase.isInStock(itemId, requestedQuantity);

         assertFalse(isInStock);

         verify(itemPersistencePort, times(1)).findById(itemId);
     }


     @Test
     void testIsInStock_ItemDoesNotExist() {
         Long itemId = 1L;
         Integer requestedQuantity = 5;

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.empty());

         NotFoundException exception = assertThrows(NotFoundException.class, () -> itemUseCase.isInStock(itemId, requestedQuantity));

         assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());

         verify(itemPersistencePort, times(1)).findById(itemId);
     }

     @Test
     void testGetAllCategoriesByItemId_ItemExists() {
         Long itemId = 1L;
         List<Long> expectedCategories = Arrays.asList(1L, 2L, 3L);
         Item item = new Item(1L, "Item2", "Description2", 10, new BigDecimal("19.99"), categories, brand);

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.of(item));
         when(itemPersistencePort.getAllCategoriesByItemId(itemId)).thenReturn(expectedCategories);

         List<Long> itemsCategories = itemUseCase.getAllCategoriesByItemId(itemId);

         assertEquals(expectedCategories, itemsCategories);
         verify(itemPersistencePort).getAllCategoriesByItemId(itemId);
     }

     @Test
     void testGetAllCategoriesByItemId_ItemDoesNotExist() {
         Long itemId = 1L;

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.empty());

         NotFoundException exception = assertThrows(NotFoundException.class, () -> itemUseCase.getAllCategoriesByItemId(itemId));

         assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());
         verify(itemPersistencePort).findById(itemId);
     }

     @Test
     void testGetItemsPaginated() {
         List<Long> itemsId =List.of(1L, 2L);
         PaginationRequestItems request = new PaginationRequestItems(1, 10, SortDirection.ASC, Filter.CATEGORYNAME, "Electronicos",itemsId);
         List<Item> items = List.of(new Item(1L, "Item1", "Description1", 10, new BigDecimal("19.99"), categories, brand),
                            new Item(2L, "Item2", "Description2", 10, new BigDecimal("19.99"), categories, brand));
         Paginated<Item> expectedPaginatedItems = new Paginated<>(items, 0, 1, 2);

         when(itemPersistencePort.getItemsPaginated(request)).thenReturn(expectedPaginatedItems);

         Paginated<Item> result = itemUseCase.getItemsPaginated(request);

         assertEquals(expectedPaginatedItems, result);
         verify(itemPersistencePort).getItemsPaginated(request);
     }

     @Test
     void testItemExist_ItemDoesNotExist() {
         Long itemId = 1L;

         when(itemPersistencePort.findById(itemId)).thenReturn(Optional.empty());

         NotFoundException exception = assertThrows(NotFoundException.class, () -> itemUseCase.getAllCategoriesByItemId(itemId));

         assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());
         verify(itemPersistencePort, times(1)).findById(itemId);
     }

     @Test
     void testGetItemsWithPrice() {
         List<Long> itemIds = Arrays.asList(1L, 2L, 3L);
         List<Item> expectedItemsWithPrice = Arrays.asList(
                 new Item(1L, "Item1", "Description1", 10, new BigDecimal("19.99"), categories, brand),
                 new Item(2L, "Item2", "Description2", 10, new BigDecimal("19.99"), categories, brand),
                 new Item(3L, "Item3", "Description3", 10, new BigDecimal("19.99"), categories, brand));

         when(itemPersistencePort.getItemsWithPrice(itemIds)).thenReturn(expectedItemsWithPrice);

         List<Item> result = itemUseCase.getItemsWithPrice(itemIds);

         assertEquals(expectedItemsWithPrice, result);
         verify(itemPersistencePort).getItemsWithPrice(itemIds);
     }










 }
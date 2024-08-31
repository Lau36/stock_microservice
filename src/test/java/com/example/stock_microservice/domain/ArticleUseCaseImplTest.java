package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.IArticlePersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.domain.usecases.ArticleUseCaseImpl;
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

 class ArticleUseCaseImplTest {
     @Mock
     private IArticlePersistencePort articlePersistencePort;

     @Mock
     private IBrandPersistencePort brandPersistencePort;

     @Mock
     private ICategoryPersistencePort categoryPersistencePort;

     @InjectMocks
     private ArticleUseCaseImpl articleUseCase;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);
     }

     @Test
     void testCreateArticle_Success() {
         // Configuración
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Article article = new Article(
                 null,
                 "New Article",
                 "Description of the new article",
                 10,
                 new BigDecimal("29.99"),
                 categoryIds,
                 100L
         );

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test"), new Category(2L, "Category 2", "Description test")));
         when(articlePersistencePort.saveArticle(article)).thenReturn(article);

         // Ejecución
         Article createdArticle = articleUseCase.createArticle(article);

         // Verificación
         assertNotNull(createdArticle);
         assertEquals("New Article", createdArticle.getName());
         verify(articlePersistencePort).saveArticle(article);
     }

     @Test
     void testCreateArticle_ThrowsNotNullExceptionForCategories() {
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), null, 100L);

         NotNullException thrown = assertThrows(NotNullException.class, () -> articleUseCase.createArticle(article));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsExceededMaximunCategories() {
         List<Long> categoryIds = Arrays.asList(1L, 2L, 3L, 4L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         ExceededMaximunCategories thrown = assertThrows(ExceededMaximunCategories.class, () -> articleUseCase.createArticle(article));
         assertEquals(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE, thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsEmptyFieldExceptionForCategories() {
         List<Long> categoryIds = Arrays.asList();
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         EmptyFieldException thrown = assertThrows(EmptyFieldException.class, () -> articleUseCase.createArticle(article));
         assertEquals("CATEGORIAS", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsDuplicateCategoriesException() {
         List<Long> categoryIds = Arrays.asList(1L, 1L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         DuplicateCategoriesException thrown = assertThrows(DuplicateCategoriesException.class, () -> articleUseCase.createArticle(article));
         assertEquals(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION, thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotNullExceptionForBrand() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, null);

         NotNullException thrown = assertThrows(NotNullException.class, () -> articleUseCase.createArticle(article));
         assertEquals("ID_BRAND", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsAlreadyExistsException() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.of(article));

         AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () -> articleUseCase.createArticle(article));
         assertEquals("Article", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotFoundExceptionForBrand() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> articleUseCase.createArticle(article));
         assertEquals("100", thrown.getMessage());
     }

     @Test
     void testCreateArticle_ThrowsNotFoundExceptionForCategories() {
         List<Long> categoryIds = Arrays.asList(1L, 2L);
         Article article = new Article(null, "Article", "Description", 10, new BigDecimal("19.99"), categoryIds, 100L);

         when(articlePersistencePort.findByName(anyString())).thenReturn(Optional.empty());
         when(brandPersistencePort.findById(anyLong())).thenReturn(Optional.of(mock(Brand.class)));
         when(categoryPersistencePort.findAllById(categoryIds)).thenReturn(List.of(new Category(1L, "Category 1", "Description test")));

         NotFoundException thrown = assertThrows(NotFoundException.class, () -> articleUseCase.createArticle(article));
         assertEquals("Alguna de estas categorias'"+article.getId_categories(), thrown.getMessage());
     }

 }
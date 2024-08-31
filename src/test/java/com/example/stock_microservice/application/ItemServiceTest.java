package com.example.stock_microservice.application;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.ports.input.IArticleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {
    @Mock
    private IArticleUseCase articleUseCase;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle() {
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Test Article",
                "This is a test description",
                10,
                new BigDecimal("19.99"),
                categoryIds,
                100L
        );

        when(articleUseCase.createArticle(any(Article.class))).thenReturn(article);

        Article createdArticle = itemService.createArticle(article);

        verify(articleUseCase).createArticle(article);

        assertEquals(article, createdArticle);
    }
}

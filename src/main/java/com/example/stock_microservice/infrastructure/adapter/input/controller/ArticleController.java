package com.example.stock_microservice.infrastructure.adapter.input.controller;


import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddArticleRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.ArticleResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ArticleRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ArticleResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Article")
@RequiredArgsConstructor
public class ArticleController {
    private final ItemService itemService;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody AddArticleRequest addArticleRequest) {
        Article createdArticle = itemService.createArticle(articleRequestMapper.toArticle(addArticleRequest));
        ArticleResponse articleResponse = articleResponseMapper.toAddArticleResponse(createdArticle);
        return ResponseEntity.ok(articleResponse);
    }

}

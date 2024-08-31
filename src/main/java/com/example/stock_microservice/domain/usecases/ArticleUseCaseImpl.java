package com.example.stock_microservice.domain.usecases;

import com.example.stock_microservice.domain.execptions.*;
import com.example.stock_microservice.domain.models.Article;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.input.IArticleUseCase;
import com.example.stock_microservice.domain.ports.output.IArticlePersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.utils.DomainConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleUseCaseImpl implements IArticleUseCase {

    private final IArticlePersistencePort articlePersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ArticleUseCaseImpl(IArticlePersistencePort articlePersistencePort, IBrandPersistencePort brandPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Article createArticle(Article article) {

        if(article.getId_categories() == null){
            throw new NotNullException(DomainConstants.Field.CATEGORIAS.toString());
        }
        if (article.getId_categories().size() > 3){
            throw new ExceededMaximunCategories(DomainConstants.EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE);
        }
        if(article.getId_categories().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.CATEGORIAS.toString());
        }
        Set<Long> categoryIds = new HashSet<>(article.getId_categories());
        if(categoryIds.size() < article.getId_categories().size()){
            throw new DuplicateCategoriesException(DomainConstants.DUPLICATE_CATEGORIES_EXCEPTION);
        }
        if(article.getId_brand() == null){
            throw new NotNullException(DomainConstants.Field.ID_BRAND.toString());
        }
        if(articlePersistencePort.findByName(article.getName()).isPresent()){
            throw new AlreadyExistsException(article.getName());
        }
        if(brandPersistencePort.findById(article.getId_brand()).isEmpty()){
            throw new NotFoundException(article.getId_brand().toString());
        }
        List<Category> categories = categoryPersistencePort.findAllById(article.getId_categories());
        if(categories.size() < article.getId_categories().size()){
            throw new NotFoundException("Alguna de estas categorias'"+article.getId_categories().toString());
        }
        return articlePersistencePort.saveArticle(article);
    }
}

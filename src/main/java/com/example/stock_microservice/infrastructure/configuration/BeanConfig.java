package com.example.stock_microservice.infrastructure.configuration;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.usecases.CategoryUseCaseImplement;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.CategoryPersistenceAdapterMySql;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CategoryService categoryService(final ICategoryPersistencePort categoryPersistencePort){
        return new CategoryService(new CategoryUseCaseImplement(categoryPersistencePort));
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(final CategoryRepository categoryRepository, final CategoryMapper categoryMapper) {
        return new CategoryPersistenceAdapterMySql(categoryRepository, categoryMapper);
    }
}

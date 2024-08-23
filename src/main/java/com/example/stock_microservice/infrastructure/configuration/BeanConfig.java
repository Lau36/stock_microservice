package com.example.stock_microservice.infrastructure.configuration;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.application.usecases.CategoryUseCaseImplement;
import com.example.stock_microservice.domain.ports.output.CategoryPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.CategoryPersistenceAdapterMySql;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CategoryService categoryService(final CategoryPersistencePort categoryPersistencePort){
        return new CategoryService(new CategoryUseCaseImplement(categoryPersistencePort));
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort(final CategoryRepository categoryRepository, final CategoryMapper categoryMapper) {
        return new CategoryPersistenceAdapterMySql(categoryRepository, categoryMapper);
    }
}

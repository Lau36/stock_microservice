package com.example.stock_microservice.infrastructure.configuration;

import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.usecases.ItemUseCaseImpl;
import com.example.stock_microservice.domain.usecases.BrandUseCaseImpl;
import com.example.stock_microservice.domain.usecases.CategoryUseCaseImplement;
import com.example.stock_microservice.domain.ports.output.ICategoryPersistencePort;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.PaginatedItemResponseMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.ItemPersistenceAdapterMySql;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.BrandPersistenceAdapterMySql;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.CategoryPersistenceAdapterMySql;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.BrandMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.CategoryMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.ItemMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.ItemRepository;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
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

    @Bean
    public IBrandPersistencePort brandPersistencePort(final BrandRepository brandRepository, final BrandMapper brandMapper) {
        return new BrandPersistenceAdapterMySql(brandRepository, brandMapper);
    }

    @Bean
    public BrandService brandService(final IBrandPersistencePort brandPersistencePort) {
        return new BrandService(new BrandUseCaseImpl(brandPersistencePort));
    }

    @Bean
    public IItemPersistencePort articlePersistencePort(final ItemRepository itemRepository, final ItemMapper itemMapper, final CategoryRepository categoryRepository, final BrandRepository brandRepository) {
        return new ItemPersistenceAdapterMySql(itemRepository, categoryRepository, brandRepository, itemMapper);
    }

    @Bean
    public ItemService articleService(final IItemPersistencePort articlePersistencePort, final IBrandPersistencePort brandPersistencePort, final ICategoryPersistencePort categoryPersistencePort) {
        return new ItemService(new ItemUseCaseImpl(articlePersistencePort, brandPersistencePort, categoryPersistencePort));
    }

    @Bean
    public AddItemMapper addItemMapper(final ICategoryPersistencePort categoryPersistencePort, final IBrandPersistencePort brandPersistencePort) {
        return new AddItemMapper(categoryPersistencePort, brandPersistencePort);
    }

    @Bean
    public PaginatedItemResponseMapper paginatedItemResponseMapper(){
        return new PaginatedItemResponseMapper();
    }

}

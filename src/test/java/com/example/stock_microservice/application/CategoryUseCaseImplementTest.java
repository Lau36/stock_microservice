package com.example.stock_microservice.application;

import com.example.stock_microservice.application.usecases.CategoryUseCaseImplement;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.ports.output.CategoryPersistencePort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryUseCaseImplementTest {
    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCaseImplement categoryUseCaseImplement;

}

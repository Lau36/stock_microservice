package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.usecases.BrandUseCaseImpl;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BrandUseCaseImplTest {
    @Mock
    IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    BrandUseCaseImpl brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        brand = new Brand(1L, "BrandTest1", "Brand test1");
    }

    @Test
    void testCreateBrandSuccess() {
        when(brandPersistencePort.findByBrandName(brand.getName())).thenReturn(Optional.empty());
        when(brandPersistencePort.save(brand)).thenReturn(brand);

        Brand createdBrand = brandUseCase.createBrand(brand);

        assertEquals(createdBrand, brand);
        verify(brandPersistencePort, Mockito.times(1)).findByBrandName(brand.getName());
        verify(brandPersistencePort, Mockito.times(1)).save(brand);
    }

    @Test
    void testCreateBrandAlreadyExists() {
    when(brandPersistencePort.findByBrandName(brand.getName())).thenReturn(Optional.of(brand));
    assertThrows(AlreadyExistsException.class, () -> brandUseCase.createBrand(brand));

    verify(brandPersistencePort, Mockito.times(1)).findByBrandName(brand.getName());
    verify(brandPersistencePort, never()).save(brand);
    }

    @Test
    void testCreateBrand_shouldThrowEmptyFieldException_whenNameIsEmpty() {
        Brand brand1 = new Brand(1L,"", "Brand test1");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessageContaining(DomainConstants.Field.NAME.toString());

        verify(brandPersistencePort, never()).save(any(Brand.class));
    }
    @Test
    void testCreateBrand_shouldThrowEmptyFieldException_whenDescriptionIsEmpty() {
        Brand brand1 = new Brand(1L,"BrandTest1", "");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessageContaining(DomainConstants.Field.DESCRIPTION.toString());

        verify(brandPersistencePort, never()).save(any(Brand.class));
    }

    @Test
    void testCreateBrand_shouldThrowMaxLengthExceededException_whenNameExceedsMaxLength(){
        Brand brand1 = new Brand(1L,"A very long brandName A very long brandName A very long brandName", "Brand test1");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(MaxLengthExceededException.class)
                .hasMessageContaining(DomainConstants.Field.NAME.toString());

        verify(brandPersistencePort, never()).save(brand1);
    }

    @Test
    void testCreateBrand_shouldThrowMaxLengthExceededException_whenDescriptionExceedsMaxLength(){
        Brand brand1 = new Brand(1L,"BrandName", "A very long brand description A very long brand description A very long brand description A very long brand description A very long brand description A very long brand description");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(MaxLengthExceededException.class)
                .hasMessageContaining(DomainConstants.Field.DESCRIPTION.toString());

        verify(brandPersistencePort, never()).save(brand1);
    }



}

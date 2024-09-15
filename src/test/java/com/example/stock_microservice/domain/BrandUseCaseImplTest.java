package com.example.stock_microservice.domain;

import com.example.stock_microservice.domain.execptions.AlreadyExistsException;
import com.example.stock_microservice.domain.execptions.EmptyFieldException;
import com.example.stock_microservice.domain.execptions.MaxLengthExceededException;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.ports.output.IBrandPersistencePort;
import com.example.stock_microservice.domain.usecases.BrandUseCaseImpl;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
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
        MockitoAnnotations.openMocks(this);
        brand = new Brand(1L, "BrandTest1", "Brand test1");
    }

    @Test
    void testCreateBrandSuccess() {
        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.empty());
        when(brandPersistencePort.save(brand)).thenReturn(brand);

        Brand createdBrand = brandUseCase.createBrand(brand);

        assertEquals(createdBrand, brand);
        verify(brandPersistencePort, Mockito.times(1)).findByName(brand.getName());
        verify(brandPersistencePort, Mockito.times(1)).save(brand);
    }

    @Test
    void testCreateBrandAlreadyExists() {
    when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.of(brand));
    assertThrows(AlreadyExistsException.class, () -> brandUseCase.createBrand(brand));

    verify(brandPersistencePort, Mockito.times(1)).findByName(brand.getName());
    verify(brandPersistencePort, never()).save(brand);
    }

    @Test
    void testCreateBrand_shouldThrowEmptyFieldException_whenNameIsEmpty() {
        Brand brand1 = new Brand(1L,"", "Brand test1");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessageContaining(DomainConstants.Field.NOMBRE.toString());

        verify(brandPersistencePort, never()).save(any(Brand.class));
    }

    @Test
    void testCreateBrand_shouldThrowEmptyFieldException_whenDescriptionIsEmpty() {
        Brand brand1 = new Brand(1L,"BrandTest1", "");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessageContaining(DomainConstants.Field.DESCRIPCION.toString());

        verify(brandPersistencePort, never()).save(any(Brand.class));
    }

    @Test
    void testCreateBrand_shouldThrowMaxLengthExceededException_whenNameExceedsMaxLength(){
        Brand brand1 = new Brand(1L,"A very long brandName A very long brandName A very long brandName", "Brand test1");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(MaxLengthExceededException.class)
                .hasMessageContaining(DomainConstants.Field.NOMBRE.toString());

        verify(brandPersistencePort, never()).save(brand1);
    }

    @Test
    void testCreateBrand_shouldThrowMaxLengthExceededException_whenDescriptionExceedsMaxLength(){
        Brand brand1 = new Brand(1L,"BrandName", "A very long brand description A very long brand description A very long brand description A very long brand description A very long brand description A very long brand description");
        assertThatThrownBy(() -> brandUseCase.createBrand(brand1))
                .isInstanceOf(MaxLengthExceededException.class)
                .hasMessageContaining(DomainConstants.Field.DESCRIPCION.toString());

        verify(brandPersistencePort, never()).save(brand1);
    }

    @Test
    void testCreateBrand_shouldNotThrowException_whenNameIsAtMaxLength(){
        String maxLengthName = "B".repeat(50);
        Brand brandTest = new Brand(1L, maxLengthName, "Valid description");

        when(brandPersistencePort.findByName(maxLengthName)).thenReturn(Optional.empty());
        when(brandPersistencePort.save(brandTest)).thenReturn(brandTest);

        Brand result = brandUseCase.createBrand(brandTest);

        assertEquals(brandTest, result);
        verify(brandPersistencePort, times(1)).save(brandTest);
    }

    @Test
    void testCreateBrand_shouldNotThrowException_whenDescriptionIsAtMaxLength(){
        String maxLengthDescription = "B".repeat(90);
        Brand brandTest = new Brand(1L, "ValidName", maxLengthDescription);

        when(brandPersistencePort.findByName("ValidName")).thenReturn(Optional.empty());
        when(brandPersistencePort.save(brandTest)).thenReturn(brandTest);

        Brand result = brandUseCase.createBrand(brandTest);

        assertEquals(brandTest, result);
        verify(brandPersistencePort, times(1)).save(brandTest);
    }

    @Test
    void testListAllBrands(){
        PaginationRequest paginationRequest = new PaginationRequest(0, 10, "name", SortDirection.ASC);
        PaginatedBrands paginatedBrands = new PaginatedBrands(Arrays.asList(brand,brand),0,4,2);
        when(brandPersistencePort.listAllBrands(paginationRequest)).thenReturn(paginatedBrands);

        PaginatedBrands result = brandUseCase.listAllBrands(paginationRequest);
        assertEquals(paginatedBrands, result);
        verify(brandPersistencePort, times(1)).listAllBrands(paginationRequest);
    }



}

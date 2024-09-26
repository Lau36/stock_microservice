package com.example.stock_microservice.infrastructure.adapter.output.persistence.repository;

import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String articleName);

    @Query("SELECT i FROM ItemEntity i JOIN i.categories c WHERE c.categoryName = :categoryName AND i.id IN (:ids)")
    Page<ItemEntity> findAllByCategoryAndIds(@Param("categoryName") String categoryName, @Param("ids") List<Long> ids, Pageable pageable);

    @Query("SELECT i FROM ItemEntity i JOIN i.brand b WHERE b.name = :brandName AND i.id IN :ids")
    Page<ItemEntity> findAllByBrandAndIds(@Param("brandName") String brandName, @Param("ids") List<Long> ids, Pageable pageable);

    @Query("SELECT i FROM ItemEntity i JOIN i.categories c JOIN i.brand b WHERE c.categoryName = :categoryName AND b.name = :brandName AND i.id IN :ids")
    Page<ItemEntity> findAllByCategoryNameAndBrandNameAndIds(@Param("categoryName") String categoryName,
                                                             @Param("brandName") String brandName,
                                                             @Param("ids") List<Long> ids,
                                                             Pageable pageable);
    Page<ItemEntity> findPaginatedByIdIn(List<Long> ids, Pageable pageable);
    List<ItemEntity> findAllByIdIn(List<Long> ids);
}

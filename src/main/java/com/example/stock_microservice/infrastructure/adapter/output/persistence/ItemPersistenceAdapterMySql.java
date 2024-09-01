package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.execptions.NotFoundException;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.BrandEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.ItemMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.BrandRepository;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.ItemRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

public class ItemPersistenceAdapterMySql implements IItemPersistencePort {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ItemMapper itemMapper;

    @Override
    public Item saveArticle(Item item) {

        List<CategoryEntity> managedCategories = item.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new NotFoundException("Category not found: " + category.getId())))
                .toList();

        BrandEntity managedBrand = brandRepository.findById(item.getBrand().getId())
                .orElseThrow(() -> new NotFoundException("Brand not found: " + item.getBrand().getId()));

        ItemEntity itemEntity = itemMapper.toItemEntity(item);
        itemEntity.setCategories(managedCategories);
        itemEntity.setBrand(managedBrand);

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);
        return itemMapper.toItem(savedItemEntity);
    }

    @Override
    public Optional<Item> findByName(String articleName) {
        Optional<ItemEntity> articleEntity = itemRepository.findByName(articleName);
        return articleEntity.map(itemMapper::toItem);
    }

    @Override
    public Paginated<Item> listAllItemsPaginated(PaginationRequest paginationRequest) {
//        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()), paginationRequest.getSort());
//        Pageable pageable = PageRequest.of(paginationRequest.getPage(),paginationRequest.getSize(), sort);
//        Page<ItemEntity> itemEntities = itemRepository.findAll(pageable);
//        List<Item> items = itemEntities.stream().map(itemEntity -> {
//            List<CategoryNameId> categories = itemEntity.getCategories().stream().map(categoryEntity ->
//                new CategoryNameId(categoryEntity.getId(),categoryEntity.getCategoryName())).toList();
//
//            BranNameId brand = new BranNameId(itemEntity.getBrand().getId(),itemEntity.getBrand().getName());
//
//            return new Item(
//                    itemEntity.getId(),
//                    itemEntity.getName(),
//                    itemEntity.getDescription(),
//                    itemEntity.getAmount(),
//                    itemEntity.getPrice(),
//
//            )
//
//        }).toList();
        return null;
    }

}

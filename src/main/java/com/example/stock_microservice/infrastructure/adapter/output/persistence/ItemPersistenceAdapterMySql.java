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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()), paginationRequest.getSort());
        if (paginationRequest.getSort().equalsIgnoreCase("brandName")) {
            sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()), "brand.name");
        }
        if(paginationRequest.getSort().equalsIgnoreCase("categoryName")){
            sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()), "categories.categoryName");
        }
        if(paginationRequest.getSort().equalsIgnoreCase("itemName")){
            sort = Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection().name()), "name");
        }
        Pageable pageable = PageRequest.of(paginationRequest.getPage(),paginationRequest.getSize(), sort);
        Page<ItemEntity> itemEntities = itemRepository.findAll(pageable);
        List<Item> itemsList = itemEntities.stream().map(itemMapper::toItem).toList();
        return new Paginated<>(itemsList,itemEntities.getNumber(),itemEntities.getTotalPages(),itemEntities.getTotalElements());
    }

    @Override
    public Item addStock(Long id, Integer quantity) {
        ItemEntity item = itemRepository.findById(id).orElseThrow();
        item.setAmount(item.getAmount() + quantity);
        ItemEntity newItem = itemRepository.save(item);
        return itemMapper.toItem(newItem);
    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(id);
        return itemEntity.map(itemMapper::toItem);
    }

    @Override
    public List<Long> getAllCategoriesByItemId(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow();
        return itemEntity.getCategories().stream().map(CategoryEntity::getId).toList();
    }

}

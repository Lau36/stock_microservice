package com.example.stock_microservice.infrastructure.adapter.output.persistence;

import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.ports.output.IItemPersistencePort;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.dto.BranNameId;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.dto.CategoryNameId;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.entity.ItemEntity;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.mapper.ItemMapper;
import com.example.stock_microservice.infrastructure.adapter.output.persistence.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor

public class ItemPersistenceAdapterMySql implements IItemPersistencePort {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public Item saveArticle(Item item) {
        ItemEntity itemEntity = itemMapper.toArticleEntity(item);
        ItemEntity savedItemEntity = itemRepository.save(itemEntity);
        return itemMapper.toArticle(savedItemEntity);
    }

    @Override
    public Optional<Item> findByName(String articleName) {
        Optional<ItemEntity> articleEntity = itemRepository.findByName(articleName);
        return articleEntity.map(itemMapper::toArticle);
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

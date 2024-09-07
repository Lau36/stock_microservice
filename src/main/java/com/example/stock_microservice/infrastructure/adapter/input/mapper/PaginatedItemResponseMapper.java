package com.example.stock_microservice.infrastructure.adapter.input.mapper;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.ItemResponseDTO;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.NameIdDTO;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.PaginatedItemResponse;
import java.util.List;

public class PaginatedItemResponseMapper {
    public PaginatedItemResponse toPaginatedItemResponse(Paginated<Item> paginatedItems) {
        List<ItemResponseDTO> itemResponseDTOs = paginatedItems.getContent().stream().map(item -> {
            List<NameIdDTO> categoryDTOs = item.getCategories().stream()
                    .map(category -> new NameIdDTO(category.getId(), category.getCategoryName())).toList();
            NameIdDTO brandDTO = new NameIdDTO(item.getBrand().getId(), item.getBrand().getName());
            return new ItemResponseDTO(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAmount(),
                    item.getPrice(),
                    categoryDTOs,
                    brandDTO
            );
        }).toList();
        return new PaginatedItemResponse(itemResponseDTOs,paginatedItems.getCurrentPage(), paginatedItems.getTotalPages(),paginatedItems.getTotalElements());
    }
}

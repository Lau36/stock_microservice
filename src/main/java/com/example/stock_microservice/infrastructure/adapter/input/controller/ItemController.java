package com.example.stock_microservice.infrastructure.adapter.input.controller;


import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddStockRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.AddStockResponse;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.PaginatedItemResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemResponseMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.PaginatedItemResponseMapper;
import com.example.stock_microservice.utils.DomainConstants;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final AddItemMapper addItemMapper;
    private final ItemResponseMapper itemResponseMapper;
    private final PaginatedItemResponseMapper paginatedItemResponseMapper;

    @PostMapping
    public ResponseEntity<AddItemResponse> createItem(@RequestBody AddItemRequest addItemRequest) {
        Item createdItem = itemService.createItem(addItemMapper.toItem(addItemRequest));
        AddItemResponse response = itemResponseMapper.toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,createdItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<PaginatedItemResponse> getAllItemsPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                                      @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                                      @RequestParam(defaultValue = "name") String sort,
                                                                      @RequestParam(defaultValue = "asc")  String sortDirection){
        PaginationRequest paginationRequest = new PaginationRequest(page,size,sort, SortDirection.valueOf(sortDirection.toUpperCase()));
        Paginated<Item> paginatedItems = itemService.getItems(paginationRequest);
        PaginatedItemResponse response = paginatedItemResponseMapper.toPaginatedItemResponse(paginatedItems);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping
    public ResponseEntity<AddStockResponse> updateItem(@RequestBody AddStockRequest addStockRequest) {
        Item item = itemService.addStock(addStockRequest.getId(), addStockRequest.getQuantity());
        AddStockResponse response = new AddStockResponse(item.getName(), item.getAmount());
        return ResponseEntity.ok(response);
    }
}

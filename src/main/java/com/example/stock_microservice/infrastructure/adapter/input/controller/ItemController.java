package com.example.stock_microservice.infrastructure.adapter.input.controller;


import com.example.stock_microservice.application.services.ItemService;
import com.example.stock_microservice.domain.models.Item;
import com.example.stock_microservice.domain.utils.Paginated;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddItemRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddStockRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.*;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.AddItemMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.ItemResponseMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.PaginatedItemResponseMapper;
import com.example.stock_microservice.utils.DomainConstants;
import com.example.stock_microservice.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final AddItemMapper addItemMapper;
    private final ItemResponseMapper itemResponseMapper;
    private final PaginatedItemResponseMapper paginatedItemResponseMapper;


    @Operation(summary = SwaggerConstants.CREATE_ITEM_SUMMARY,
            description = SwaggerConstants.CREATE_ITEM_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerConstants.ITEM_CREATED_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddItemResponse.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @PostMapping
    public ResponseEntity<AddItemResponse> createItem(@RequestBody AddItemRequest addItemRequest) {
        Item createdItem = itemService.createItem(addItemMapper.toItem(addItemRequest));
        AddItemResponse response = itemResponseMapper.toAddItemResponse(DomainConstants.SUCCESSFUL_CREATED_ITEM_MESSAGE,createdItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = SwaggerConstants.GET_ALL_ITEMS_PAGINATED_SUMMARY,
            description = SwaggerConstants.GET_ALL_ITEMS_PAGINATED_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.ITEM_LIST_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedItemResponse.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @Parameters({
            @Parameter(name = "page", description = SwaggerConstants.PAGE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
            @Parameter(name = "size", description = SwaggerConstants.SIZE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "10", minimum = "1")),
            @Parameter(name = "sort", description = SwaggerConstants.SORT_PARAM_DESCRIPTION, schema = @Schema(type = "string", defaultValue = "name")),
            @Parameter(name = "sortDirection", description = SwaggerConstants.SORT_DIRECTION_PARAM_DESCRIPTION, schema = @Schema(type = "string", allowableValues = {"asc", "desc"}, defaultValue = "asc"))
    })
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

    @Operation(summary = SwaggerConstants.UPDATE_ITEM_SUMMARY,
            description = SwaggerConstants.UPDATE_ITEM_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.ITEM_UPDATED_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddStockResponse.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @PatchMapping
    public ResponseEntity<AddStockResponse> updateItem(@RequestBody AddStockRequest addStockRequest) {
        Item item = itemService.addStock(addStockRequest.getId(), addStockRequest.getQuantity());
        AddStockResponse response = new AddStockResponse(item.getName(), item.getAmount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/InStock ")
    public ResponseEntity<Boolean> isInStock(@RequestParam int itemId, @RequestParam int quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.isInStock((long) itemId, quantity));
    }

    @GetMapping("/Categories")
    public ResponseEntity<List<Long>> getCategoriesByItemId(@RequestParam int itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllCategoriesByItemId((long) itemId));
    }
}

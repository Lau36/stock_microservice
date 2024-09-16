package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.CategoryService;
import com.example.stock_microservice.domain.utils.PaginatedCategories;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.domain.models.Category;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddCategoryRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.CategoryResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.CategoryResponseMapper;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Operation(summary = SwaggerConstants.CREATE_CATEGORY_SUMMARY,
            description = SwaggerConstants.CREATE_CATEGORY_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerConstants.CATEGORY_CREATED_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody AddCategoryRequest addCategoryRequest){
        Category createdCategory = categoryService.createCategory(categoryRequestMapper.addRequestToCategory(addCategoryRequest));
        CategoryResponse createdCategoryResponse = categoryResponseMapper.fromCategory(createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryResponse);
    }

    @Operation(summary = SwaggerConstants.GET_ALL_CATEGORIES_SUMMARY,
            description = SwaggerConstants.GET_ALL_CATEGORIES_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.CATEGORY_LIST_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<Category> listOfCategories = categoryService.getAll();
        List<CategoryResponse> listOfCategoriesResponse = categoryResponseMapper.toCategoryResponses(listOfCategories);
        return ResponseEntity.status(HttpStatus.OK).body(listOfCategoriesResponse);
    }

    @Operation(summary = SwaggerConstants.GET_ALL_CATEGORIES_PAGINATED_SUMMARY,
            description = SwaggerConstants.GET_ALL_CATEGORIES_PAGINATED_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.CATEGORY_LIST_RESPONSE,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedCategories.class))),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT)
    })
    @Parameters({
            @Parameter(
                    name = "page", description = SwaggerConstants.PAGE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
            @Parameter(name = "size", description = SwaggerConstants.SIZE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "10", minimum = "1")),
            @Parameter(name = "sort", description = SwaggerConstants.SORT_PARAM_DESCRIPTION, schema = @Schema(type = "string", defaultValue = "categoryName")),
            @Parameter(name = "sortDirection", description = SwaggerConstants.SORT_DIRECTION_PARAM_DESCRIPTION, schema = @Schema(type = "string", allowableValues = { "asc", "desc" }, defaultValue = "asc"))
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedCategories> getAllCategoriesPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                                         @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                                         @RequestParam(defaultValue = "categoryName") String sort,
                                                                         @RequestParam(defaultValue = "asc")  String sortDirection){
        PaginationRequest paginationRequest = new PaginationRequest(page,size,sort,SortDirection.valueOf(sortDirection.toUpperCase()));
        PaginatedCategories categories = categoryService.listCategories(paginationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}

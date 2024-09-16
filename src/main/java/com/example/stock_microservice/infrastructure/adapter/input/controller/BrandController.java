package com.example.stock_microservice.infrastructure.adapter.input.controller;

import com.example.stock_microservice.application.services.BrandService;
import com.example.stock_microservice.domain.models.Brand;
import com.example.stock_microservice.domain.utils.PaginatedBrands;
import com.example.stock_microservice.domain.utils.PaginationRequest;
import com.example.stock_microservice.domain.utils.SortDirection;
import com.example.stock_microservice.infrastructure.adapter.input.dto.request.AddBrandRequest;
import com.example.stock_microservice.infrastructure.adapter.input.dto.response.BrandResponse;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandRequestMapper;
import com.example.stock_microservice.infrastructure.adapter.input.mapper.BrandResponseMapper;
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

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Operation(
            summary = SwaggerConstants.CREATE_BRAND_SUMMARY,
            description = SwaggerConstants.CREATE_BRAND_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.BRAND_CREATED_RESPONSE,
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddBrandRequest.class)) }),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT,
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody AddBrandRequest addBrandRequest) {
        Brand createdBrand = brandService.createBrand(brandRequestMapper.addRequestToBrand(addBrandRequest));
        BrandResponse brandResponse = brandResponseMapper.toBrandResponse(createdBrand);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(
            summary = SwaggerConstants.GET_ALL_BRANDS_SUMMARY,
            description = SwaggerConstants.GET_ALL_BRANDS_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.BRAND_LIST_RESPONSE,
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedBrands.class)) }),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.INVALID_INPUT,
                    content = @Content)
    })
    @Parameters({
            @Parameter(name = "page", description = SwaggerConstants.PAGE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
            @Parameter(name = "size", description = SwaggerConstants.SIZE_PARAM_DESCRIPTION, schema = @Schema(type = "integer", defaultValue = "10", minimum = "1")),
            @Parameter(name = "sort", description = SwaggerConstants.SORT_PARAM_DESCRIPTION, schema = @Schema(type = "string", defaultValue = "name")),
            @Parameter(name = "sortDirection", description = SwaggerConstants.SORT_DIRECTION_PARAM_DESCRIPTION, schema = @Schema(type = "string", allowableValues = { "asc", "desc" }, defaultValue = "asc"))})
    @GetMapping("/")
    public ResponseEntity<PaginatedBrands> getAllBrandsPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                                         @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                                         @RequestParam(defaultValue = "name") String sort,
                                                                         @RequestParam(defaultValue = "asc")  String sortDirection){
        PaginationRequest paginationRequest = new PaginationRequest(page,size,sort, SortDirection.valueOf(sortDirection.toUpperCase()));
        PaginatedBrands brandList = brandService.listAllBrands(paginationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(brandList);
    }
}

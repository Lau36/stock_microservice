package com.example.stock_microservice.utils;

public class SwaggerConstants {
    private SwaggerConstants() {
    }

    //Categories
    public static final String CREATE_CATEGORY_SUMMARY = "Create a new category";
    public static final String CREATE_CATEGORY_DESCRIPTION = "Endpoint to create a new category by a user with role Admin";
    public static final String GET_ALL_CATEGORIES_SUMMARY = "Get all categories";
    public static final String GET_ALL_CATEGORIES_DESCRIPTION = "Endpoint so that a user with any role that is authenticated can list all categories";
    public static final String GET_ALL_CATEGORIES_PAGINATED_SUMMARY = "Get all categories with pagination";
    public static final String GET_ALL_CATEGORIES_PAGINATED_DESCRIPTION = "Retrieve a paginated list of categories";
    public static final String CATEGORY_CREATED_RESPONSE = "Category created successfully";
    public static final String CATEGORY_LIST_RESPONSE = "List of categories retrieved successfully";

    //Brands
    public static final String CREATE_BRAND_SUMMARY = "Create a new brand";
    public static final String GET_ALL_BRANDS_SUMMARY = "Get all brands with pagination";
    public static final String CREATE_BRAND_DESCRIPTION = "Creates a new brand in the system by a user with role Admin";
    public static final String GET_ALL_BRANDS_DESCRIPTION = "Endpoint so that a user with any role that is authenticated can list all brands";
    public static final String BRAND_CREATED_RESPONSE = "Brand created successfully";
    public static final String BRAND_LIST_RESPONSE = "List of brands retrieved successfully";

    // Items
    public static final String CREATE_ITEM_SUMMARY = "Create a new item";
    public static final String CREATE_ITEM_DESCRIPTION = "Endpoint to create a new item";
    public static final String GET_ALL_ITEMS_PAGINATED_SUMMARY = "Get all items with pagination";
    public static final String GET_ALL_ITEMS_PAGINATED_DESCRIPTION = "Endpoint so that a user with any role that is authenticated can list all items";
    public static final String UPDATE_ITEM_SUMMARY = "Update an item";
    public static final String UPDATE_ITEM_DESCRIPTION = "Update the stock quantity of an item by a user with role aux_bodega";
    public static final String ITEM_CREATED_RESPONSE = "Item created successfully";
    public static final String ITEM_LIST_RESPONSE = "List of items retrieved successfully";
    public static final String ITEM_UPDATED_RESPONSE = "Item updated successfully";

    //Others
    public static final String PAGE_PARAM_DESCRIPTION = "Page number (zero-based)";
    public static final String SIZE_PARAM_DESCRIPTION = "Number of elements per page";
    public static final String SORT_PARAM_DESCRIPTION = "Property to sort by";
    public static final String SORT_DIRECTION_PARAM_DESCRIPTION = "Sort direction (asc or desc)";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String BEARER = "bearerAuth";
    public static final String OK = "Todo bien";

    public static final String GET_CATEGORIES = "Obetener las categorias asociadas a un articulo";
    public static final String GET_CATEGORIES_DESCRIPTION = "Por medio del id de un articulo se obtiene la lista de categorias que están asociadas a ese articulo";

    public static final String GET_PRICES = "Obetener los precios de los articulos";
    public static final String GET_PRICES_DESCRIPTION = "Por medio de una lista de items se obtienen los precios asociados a esos items";

    public static final String GET_ITEMS_PAGINATED = "Obetener los articulos paginados";
    public static final String GET_ITEMS_PAGINATED_DESCRIPTION = "Se pueden paginar, ordenar y filtrar los articulos";






}

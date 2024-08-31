package com.example.stock_microservice.utils;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NOMBRE,
        DESCRIPCION,
        ID,
        ID_BRAND,
        CATEGORIAS
    }
    public static final String FIELD_NAME_NULL_MESSAGE = "El campo nombre no puede ser nulo";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "El campo descripcion no puede ser nulo";
    public static final String EXCEEDED_MAXIMUN_CATEGORIES_MESSAGE = "El articulo tiene mas de 3 categorias asociadas";
    public static final String DUPLICATE_CATEGORIES_EXCEPTION = "No pueden haber categorias duplicadas";
    public static final String SUCCESSFUL_CREATED_ITEM_MESSAGE ="El artículo se creó exitosamente";

}

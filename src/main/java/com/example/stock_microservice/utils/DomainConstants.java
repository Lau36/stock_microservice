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
    public static final String LESS_THAN_ONE_CATEGORIES = "El articulo no puede tener 0 categorias asociadas";
    public static final String DUPLICATE_CATEGORIES_EXCEPTION = "No pueden haber categorias duplicadas";
    public static final String CATEGORIES_EMPTY = "La lista de id de categorias no puede estar vacia";

}

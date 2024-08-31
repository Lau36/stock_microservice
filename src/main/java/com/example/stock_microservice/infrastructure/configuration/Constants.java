package com.example.stock_microservice.infrastructure.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "El campo %s no puede estar vacio";
    public static final String MAX_LENGTH_EXCEEDED_MESSAGE = " %s";
    public static final String ALREADY_EXISTS_NAME_EXCEPTION_MESSAGE = "El campo %s ya existe en la base de datos";
    public static final String EXCEEDED_MAXIMUM_CATEGORIES = "El articulo tiene más de 3 categorias asociadas";
    public static final String CATEGORIES_NULL_MESSAGE ="%s no puede ser nulo";
    public static final String DUPLICATED_CATEGORIES_MESSAGE ="No pueden haber categorias duplicadas";
    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "%s %s no existe en la bd";


}

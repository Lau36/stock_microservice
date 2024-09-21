package com.example.stock_microservice.infrastructure.configuration;

public class Roles {
    private Roles() {
        throw new IllegalStateException("Utility class");
    }
    public static final String ROLE_ADMIN = "ROLE_Admin";
    public static final String ROLE_AUX_BODEGA = "ROLE_Aux_bodega";
    public static final String ROLE_CUSTOMER = "ROLE_Admin";
}

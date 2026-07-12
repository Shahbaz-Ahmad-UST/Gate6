package com.ust.shopkart.config;

import com.ust.shopkart.support.TestEnvironment;

public record DatabaseConfig(String jdbcUrl, String username, String password) {

    public static DatabaseConfig fromContainer(String jdbcUrl, String username, String password) {
        return new DatabaseConfig(jdbcUrl, username, password);
    }

    public static DatabaseConfig fromEnvironmentCredential() {
        String jdbcUrl = required("DB_JDBC_URL");
        String username = required("DB_USER");
        String password = required("DB_PASSWORD");
        return new DatabaseConfig(jdbcUrl, username, password);
    }

    private static String required(String name) {
        String value = TestEnvironment.optional(name, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable or system property: " + name);
        }
        return value;
    }
}
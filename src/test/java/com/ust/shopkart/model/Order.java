package com.ust.shopkart.model;

import java.time.LocalDate;

public record Order(
        Long id,
        String sku,
        int qty,
        double price,
        LocalDate orderDate,
        boolean shipped
) {
}
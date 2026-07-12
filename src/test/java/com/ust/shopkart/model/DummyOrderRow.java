package com.ust.shopkart.model;

public record DummyOrderRow(
        long id,
        long cartId,
        String status,
        int totalPaise,
        String address
) {
}
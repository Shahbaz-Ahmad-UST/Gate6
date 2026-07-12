package com.ust.shopkart.model;

import java.time.Instant;

public record OrderRow(

        long id,
        long customerId,
        long cartId,
        String status,
        int totalPaise,
        String address,
        Instant createdAt,
        Instant updatedAt

) {}
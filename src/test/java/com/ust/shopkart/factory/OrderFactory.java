package com.ust.shopkart.factory;

import com.ust.shopkart.model.Order;
import com.ust.shopkart.repository.OrderRepository;

public class OrderFactory {

    private final OrderRepository repo;

    public OrderFactory(OrderRepository repo) {
        this.repo = repo;
    }

    public Order persisted(Order order) {
        return repo.save(order);
    }
}
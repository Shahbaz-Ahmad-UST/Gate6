package com.ust.shopkart.factory;

import com.ust.shopkart.model.DummyOrderRow;
import com.ust.shopkart.repository.OrderRepository;

import java.util.concurrent.atomic.AtomicLong;

public class OrderFactory {

    private final OrderRepository repository;
    private final AtomicLong cartIdSequence = new AtomicLong(1000);

    public OrderFactory(OrderRepository repository) {
        this.repository = repository;
    }

    //Creates a placed order with default values, returns the persisted row.
    public DummyOrderRow createPlacedOrder(String address) {
        long cartId = cartIdSequence.incrementAndGet();
        int totalPaise = 49900;
        long id = repository.insert(cartId, "PLACED", totalPaise, address);
        return repository.findById(id);
    }

}
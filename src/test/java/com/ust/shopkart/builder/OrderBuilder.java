package com.ust.shopkart.builder;

import com.ust.shopkart.model.Order;

import java.time.LocalDate;

public class OrderBuilder {

    private String sku = "SKU-1";
    private int qty = 1;
    private double price = 1299.00;
    private LocalDate orderDate = LocalDate.now();
    private boolean shipped = false;

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderBuilder withQty(int qty) {
        this.qty = qty;
        return this;
    }

    public OrderBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public OrderBuilder withOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderBuilder shipped() {
        this.shipped = true;
        return this;
    }

    public Order build() {
        return new Order(null, sku, qty, price, orderDate, shipped);
    }
}
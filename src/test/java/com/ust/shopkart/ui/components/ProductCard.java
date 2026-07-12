package com.ust.shopkart.ui.components;

import com.codeborne.selenide.SelenideElement;

public class ProductCard {

    private final SelenideElement root;

    public ProductCard(SelenideElement root) {
        this.root = root;
    }

    public String title() {
        return root.$("[data-test='product-title']").text();
    }

    public int price() {
        return Integer.parseInt(
                root.$("[data-test='product-price']")
                        .text()
                        .replaceAll("[^0-9]", "")
        );
    }
}
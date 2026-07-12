package com.ust.shopkart.ui.locators;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public final class HomeLocators {

    private HomeLocators() {
    }

    public static final SelenideElement HOME_TITLE = $("section.catalog-heading h1");

    public static final SelenideElement SEARCH_BOX = $("#catalog-search");

    public static final SelenideElement SEARCH_BUTTON = $x("//button[text()='Search']");

    public static final ElementsCollection PRODUCT_LIST = $$(".product-card.product");

    public static final ElementsCollection PRODUCT_NAME = $$(".product-card.product h2 button");

    public static final ElementsCollection PRODUCT_PRICE = $$(".product-card .product-footer strong");

    public static final ElementsCollection ADD_TO_CART = $$x("//button[text()='Add to cart']");
}
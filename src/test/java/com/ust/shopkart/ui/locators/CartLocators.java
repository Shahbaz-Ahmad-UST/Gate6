package com.ust.shopkart.ui.locators;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public final class CartLocators {

    private CartLocators() {
    }

    public static final SelenideElement CART_PAGE_TITLE =$x("//h1[text()='Your cart']");
    public static final ElementsCollection ITEM_NAME_LIST = $$(".cart-line");
    public static final ElementsCollection ITEM_PRICE = $$(".line-total");
    public static final SelenideElement CHECKOUT_BUTTON = $x("//button[text()='Checkout']");
    public static final SelenideElement CONTINUE_SHOPPING = $x("//button[text()='Continue shopping']");
    public static final SelenideElement TOTAL_PRICE = $x("//strong[@data-role='cart-total']");
}
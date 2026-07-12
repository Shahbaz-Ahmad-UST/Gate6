package com.ust.shopkart.ui.components;

import com.codeborne.selenide.SelenideElement;
import com.ust.shopkart.ui.pages.CartPage;

import static com.codeborne.selenide.Selenide.$;

public class Header {

    private final SelenideElement cartIcon =
            $("[data-test='cart-icon']");

    public CartBadge cartBadge() {
        return new CartBadge();
    }

    public CartPage openCart() {
        cartIcon.click();
        return new CartPage();
    }

    public Login loginComponent() {
        return new Login();
    }

    public Login logoutComponent() {
        return new Login();
    }
}
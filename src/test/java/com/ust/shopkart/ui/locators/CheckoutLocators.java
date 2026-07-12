package com.ust.shopkart.ui.locators;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public final class CheckoutLocators {

    private CheckoutLocators() {
    }

    public static final SelenideElement CHECKOUT_PAGE_TITLE = $(".checkout-form h1");
    public static final SelenideElement ADDRESS = $("#address");
    public static final SelenideElement PLACE_ORDER = $x("//button[text()='Place order']");

}
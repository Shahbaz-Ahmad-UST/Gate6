package com.ust.shopkart.ui.locators;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class OrderLocators {

    public static final SelenideElement ORDER_CONFIRM_TITLE = $x("//h1[text()='Order confirmed']");
    public static final SelenideElement ORDER_STATUS = $("[data-field='order-status']");
    public static final SelenideElement TOTAL_AMOUNT = $("[data-field='order-total']");
    public static final SelenideElement ORDER_NUMBER = $x("//p");
    public static final SelenideElement RETURN_TO_HOME =$x("//button[text()='Return to catalog']");
}

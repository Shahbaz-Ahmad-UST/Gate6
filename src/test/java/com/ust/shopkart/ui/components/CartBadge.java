package com.ust.shopkart.ui.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartBadge {

    private final SelenideElement count = $("[data-test='cart-count']");

    public int count() {
        return Integer.parseInt(
                count.shouldBe(visible).getText()
        );
    }

    public void expectedCount(int expectedCount) {
        count.shouldHave(exactText(String.valueOf(expectedCount)));
    }
}
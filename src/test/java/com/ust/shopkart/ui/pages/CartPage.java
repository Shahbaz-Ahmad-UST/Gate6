package com.ust.shopkart.ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.ust.shopkart.config.SelenideConfig;
import com.ust.shopkart.ui.locators.CartLocators;

public class CartPage extends BasePage {

    /**
     * Open Cart Page
     */
    public CartPage openCartPage() {
        openPage(SelenideConfig.cartUrl());
        return this;
    }

    /**
     * Verify Cart Page is loaded
     */
    public CartPage verifyCartPageLoaded() {
        CartLocators.CART_PAGE_TITLE
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Your cart"));
        return this;
    }

    //Verify number of items in cart
    public CartPage verifyItemCount(int expectedCount) {
        verifyCount(CartLocators.ITEM_NAME_LIST, expectedCount);
        return this;
    }


      //Verify cart is not empty
    public CartPage verifyCartNotEmpty() {
        CartLocators.ITEM_NAME_LIST.shouldBe(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    /**
     * Click Checkout
     */
    public CartPage clickCheckout() {
        click(CartLocators.CHECKOUT_BUTTON);
        return this;
    }

    /**
     * Click Continue Shopping
     */
    public CartPage clickContinueShopping() {
        click(CartLocators.CONTINUE_SHOPPING);
        return this;
    }

    /**
     * Verify Total Price is displayed
     */
    public CartPage verifyTotalPriceDisplayed() {
        CartLocators.TOTAL_PRICE
                .shouldBe(Condition.visible)
                .shouldNotBe(Condition.empty);
        return this;
    }

    /**
     * Verify Total Price
     */
    public CartPage verifyTotalPrice(String expectedPrice) {
        CartLocators.TOTAL_PRICE
                .shouldHave(Condition.exactText(expectedPrice));
        return this;
    }

    /**
     * Get Total Price
     */
    public String getTotalPrice() {
        return getText(CartLocators.TOTAL_PRICE);
    }

    /**
     * Get Item Count
     */
    public int getItemCount() {
        return count(CartLocators.ITEM_NAME_LIST);
    }

    /**
     * Verify First Item Name
     */
    public CartPage verifyFirstItemName(String expectedName) {
        CartLocators.ITEM_NAME_LIST.first()
                .shouldHave(Condition.text(expectedName));
        return this;
    }

    /**
     * Verify First Item Price
     */
    public CartPage verifyFirstItemPrice(String expectedPrice) {
        CartLocators.ITEM_PRICE.first()
                .shouldHave(Condition.text(expectedPrice));
        return this;
    }

    public CartPage verifyCartItem(String expectedName,
                                   String expectedQuantity,
                                   String expectedPrice) {

        SelenideElement item = CartLocators.ITEM_NAME_LIST.first();

        item.$("td:nth-child(1)")
                .shouldHave(Condition.exactText(expectedName));

        item.$("td:nth-child(3)")
                .shouldHave(Condition.exactText(expectedQuantity));

        item.$("td.line-total")
                .shouldHave(Condition.exactText(expectedPrice));

        return this;
    }
}
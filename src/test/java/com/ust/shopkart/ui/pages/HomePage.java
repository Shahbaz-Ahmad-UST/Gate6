package com.ust.shopkart.ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.ust.shopkart.config.SelenideConfig;
import com.ust.shopkart.ui.locators.HomeLocators;

public class HomePage extends BasePage {

    /**
     * Open Catalog Page
     */
    public HomePage openCatalog() {
        openPage(SelenideConfig.catalogUrl());
        return this;
    }

    /**
     * Verify catalog page is loaded
     */
    public HomePage verifyHomePageLoaded() {
        verifyVisible(HomeLocators.HOME_TITLE);
        return this;
    }

    /**
     * Search product
     */
    public HomePage searchProduct(String product) {
        type(HomeLocators.SEARCH_BOX, product);
        click(HomeLocators.SEARCH_BUTTON);
        return this;
    }

    /**
     * Verify number of products displayed
     */
    public HomePage verifyProductCount(int expectedCount) {
        verifyCount(HomeLocators.PRODUCT_LIST, expectedCount);
        return this;
    }

    /**
     * Click first product name
     */
    public HomePage clickFirstProduct() {
        clickFirst(HomeLocators.PRODUCT_NAME);
        return this;
    }

    /**
     * Click first Add to Cart button
     */
    public HomePage clickFirstAddToCart() {
        clickFirst(HomeLocators.ADD_TO_CART);
        return this;
    }

    /**
     * Verify at least one product is displayed
     */
    public HomePage verifyProductsDisplayed() {
        HomeLocators.PRODUCT_LIST.shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    /**
     * Get displayed product count
     */
    public int getProductCount() {
        return count(HomeLocators.PRODUCT_LIST);
    }

    /**
     * Get first product name
     */
    public String getFirstProductName() {
        return HomeLocators.PRODUCT_NAME.first().getText();
    }

    /**
     * Get first product price
     */
    public String getFirstProductPrice() {
        return HomeLocators.PRODUCT_PRICE.first().getText();
    }
}
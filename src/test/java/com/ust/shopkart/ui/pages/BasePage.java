package com.ust.shopkart.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {

    /**
     * Open relative URL
     */
    protected void openPage(String url) {
        open(url);
    }

    /**
     * Click an element
     */
    protected void click(SelenideElement element) {
        element.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    /**
     * Type text
     */
    protected void type(SelenideElement element, String text) {
        element.shouldBe(Condition.visible)
                .clear();
        element.setValue(text);
    }

    /**
     * Read text
     */
    protected String getText(SelenideElement element) {
        return element.shouldBe(Condition.visible)
                .getText();
    }

    /**
     * Verify element visible
     */
    protected void verifyVisible(SelenideElement element) {
        element.shouldBe(Condition.visible);
    }

    /**
     * Verify element hidden
     */
    protected void verifyHidden(SelenideElement element) {
        element.shouldBe(Condition.hidden);
    }

    /**
     * Verify collection size
     */
    protected void verifyCount(ElementsCollection collection, int expectedCount) {
        collection.shouldHave(size(expectedCount));
    }

    /**
     * Click first element
     */
    protected void clickFirst(ElementsCollection collection) {
        collection.first()
                .shouldBe(Condition.visible)
                .click();
    }

    /**
     * Click element by index
     */
    protected void clickAt(ElementsCollection collection, int index) {
        collection.get(index)
                .shouldBe(Condition.visible)
                .click();
    }

    /**
     * Return collection size
     */
    protected int count(ElementsCollection collection) {
        return collection.size();
    }

    /**
     * Wait until page loaded
     */
    protected void waitForPage() {
        sleep(500);
    }

}
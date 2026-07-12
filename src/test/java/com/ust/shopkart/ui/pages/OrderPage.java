package com.ust.shopkart.ui.pages;

import com.codeborne.selenide.Condition;
import com.ust.shopkart.config.SelenideConfig;
import com.ust.shopkart.ui.locators.OrderLocators;
import org.junit.jupiter.api.Assertions;
import static com.codeborne.selenide.WebDriverRunner.url;

public class OrderPage extends BasePage
{

    public OrderPage verifyOrderUrl() {

        String orderNumber = OrderLocators.ORDER_NUMBER
                .getText()
                .replace("Order #", "")
                .trim();

        OrderLocators.ORDER_NUMBER
                .shouldBe(Condition.visible)
                .shouldNotBe(Condition.empty);

        String expectedUrl = SelenideConfig.orderUrl() + "/" + orderNumber;
        Assertions.assertEquals(expectedUrl, url());

        return this;
    }

    public OrderPage verifyOrderPageLoaded() {
        OrderLocators.ORDER_CONFIRM_TITLE
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Order"));
        return this;
    }
    public OrderPage verifyOrderStatus(String expectedStatus) {
        OrderLocators.ORDER_STATUS
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(expectedStatus));

        return this;
    }
    public String getGrandTotal()
    {
        return OrderLocators.TOTAL_AMOUNT.getText();
    }

    public OrderPage verifyGrandTotal(String expectedTotal) {
        OrderLocators.TOTAL_AMOUNT
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(expectedTotal));

        return this;
    }
    public OrderPage verifyOrderNumberDisplayed() {
        String orderNumber=OrderLocators.ORDER_NUMBER.getText().replace("Order #", "");
        OrderLocators.ORDER_NUMBER
                .shouldBe(Condition.visible)
                .shouldNotBe(Condition.empty);
        return this;
    }

}

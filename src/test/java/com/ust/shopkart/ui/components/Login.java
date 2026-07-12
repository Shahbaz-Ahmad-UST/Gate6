package com.ust.shopkart.ui.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class Login {

    private final SelenideElement title = $("//*[@id=\"root\"]/div/main/div/section[1]/div/h1");
    private final SelenideElement loginPageTitle = $("//*[@id=\"root\"]/div/main/section/div/h1");

    public String getWelcomeTitle() {
        return title.shouldBe(visible).text();
    }

    public String signOut() {
        webdriver().shouldHave(urlContaining("/login"));
        return loginPageTitle.shouldBe(visible).text();
    }
}
package com.ust.shopkart.ui.locators;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public final class LoginLocators {

    private LoginLocators() {
    }

    public static final SelenideElement LOGIN_TITLE = $x("//h1[contains(text(),'Sign')]");

    public static final SelenideElement USERNAME = $("#email");

    public static final SelenideElement PASSWORD = $("#password");

    public static final SelenideElement LOGIN_BUTTON = $x("//button[@type='submit']");

    public static final SelenideElement ERROR_MESSAGE = $("div[role='alert']");
}
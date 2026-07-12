package com.ust.shopkart.ui.pages;

import com.codeborne.selenide.Condition;
import com.ust.shopkart.config.SelenideConfig;
import com.ust.shopkart.ui.locators.LoginLocators;

public class LoginPage extends BasePage {

    /**
     * Open Login Page
     */
    public LoginPage openLoginPage() {
        openPage(SelenideConfig.loginUrl());
        return this;
    }

    /**
     * Verify Login Page is loaded
     */
    public LoginPage verifyLoginPageLoaded() {
        LoginLocators.LOGIN_TITLE
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Sign"));
        return this;
    }

    /**
     * Enter Username
     */
    public LoginPage enterUsername(String username) {
        type(LoginLocators.USERNAME, username);
        return this;
    }

    /**
     * Enter Password
     */
    public LoginPage enterPassword(String password) {
        type(LoginLocators.PASSWORD, password);
        return this;
    }

    /**
     * Click Login Button
     */
    public LoginPage clickLogin() {
        click(LoginLocators.LOGIN_BUTTON);
        return this;
    }

    /**
     * Login
     */
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }

    /**
     * Verify Invalid Login
     */
    public LoginPage verifyLoginError(String expectedMessage) {
        LoginLocators.ERROR_MESSAGE
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(expectedMessage));

        return this;
    }

    /**
     * Verify Username Value
     */
    public LoginPage verifyUsername(String expectedUsername) {
        LoginLocators.USERNAME
                .shouldHave(Condition.value(expectedUsername));

        return this;
    }

    /**
     * Verify Login Button Enabled
     */
    public LoginPage verifyLoginButtonEnabled() {
        LoginLocators.LOGIN_BUTTON
                .shouldBe(Condition.enabled);

        return this;
    }

    /**
     * Verify Password is Masked
     */
    public LoginPage verifyPasswordMasked() {
        LoginLocators.PASSWORD
                .shouldHave(Condition.attribute("type", "password"));

        return this;
    }

    //error message
    public String getErrorMessage() {
        return LoginLocators.ERROR_MESSAGE.getText();
    }
}
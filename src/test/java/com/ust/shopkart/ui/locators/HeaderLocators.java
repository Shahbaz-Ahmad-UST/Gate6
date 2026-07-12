package com.ust.shopkart.ui.locators;

import org.openqa.selenium.By;

public final class HeaderLocators {

    private HeaderLocators() {
    }
    public static final By CATALOGS =By.xpath("//button[text()='Catalog']");

    public static final By CART = By.xpath("//button[text()=' Cart']");

    public static final By PROFILE = By.xpath("//span[@class='signed-in']");

    public static final By SIGN_OUT = By.xpath("//button[contains(text(),'Sign out')]");
}
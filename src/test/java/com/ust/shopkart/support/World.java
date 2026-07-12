package com.ust.shopkart.support;

import com.ust.shopkart.ui.pages.*;

public class World {

    public final LoginPage loginPage;
    public final HomePage homePage;
    public final CartPage cartPage;
    public final CheckoutPage checkoutPage;
    public final OrderPage orderPage;

    public World() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        orderPage = new OrderPage();
    }
}
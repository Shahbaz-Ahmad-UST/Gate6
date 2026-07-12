package com.ust.shopkart.stepdefs;

import com.ust.shopkart.support.World;
import com.ust.shopkart.support.TestEnvironment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {

        world.loginPage
                .openLoginPage()
                .verifyLoginPageLoaded()
                .verifyLoginButtonEnabled()
                .verifyPasswordMasked();
    }

    @When("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        String email = TestEnvironment.required("CUSTOMER_1_NAME");
        String password =TestEnvironment.required("CUSTOMER_PASSWORD");

        world.loginPage
                .enterUsername(email)
                .verifyUsername(email)
                .enterPassword(password)
                .clickLogin();
    }

    @When("I search for product {string}")
    public void i_search_for(String productName) {

        world.homePage
                .verifyHomePageLoaded()
                .searchProduct(productName)
                .verifyProductsDisplayed();
    }

    @When("I add the first product to the cart")
    public void i_add_the_first_product_to_the_cart() {

        world.homePage
                .clickFirstAddToCart();
    }

    @When("I open the cart")
    public void i_open_the_cart() {

        world.cartPage
                .openCartPage()
                .verifyCartPageLoaded()
                .verifyCartNotEmpty();
    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {

        world.cartPage
                .clickCheckout();

        world.checkoutPage
                .verifyCheckoutPageLoaded();
    }

    @When("I enter the shipping address {string}")
    public void i_enter_the_shipping_address(String address) {

        world.checkoutPage
                .enterShippingAddress(address);
    }

    @When("I place the order")
    public void i_place_the_order() {

        world.checkoutPage
                .clickPlaceOrder();
    }

    @Then("the URL should contain the generated order number")
    public void the_url_should_contain_the_generated_order_number() {

        world.orderPage
                .verifyOrderUrl();
    }
    @Then("the order confirmation page is displayed")
    public void the_order_confirmation_page_is_displayed() {

        world.orderPage
                .verifyOrderPageLoaded()
                .verifyOrderNumberDisplayed();
    }

    @Then("the order status should be {string}")
    public void the_order_status_should_be(String status) {

        world.orderPage
                .verifyOrderStatus(status);
    }

    @Then("the order total should be displayed")
    public void the_order_total_should_be_displayed() {

        String total = world.orderPage.getGrandTotal();

        world.orderPage.verifyGrandTotal(total);
    }


}
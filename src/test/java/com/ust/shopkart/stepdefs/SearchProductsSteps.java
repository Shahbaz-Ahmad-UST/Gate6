package com.ust.shopkart.stepdefs;

import com.ust.shopkart.support.World;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchProductsSteps {

    private final World world;

    public SearchProductsSteps(World world) {
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen() {
        world.homePage
                .openCatalog()
                .verifyHomePageLoaded();
    }

    @When("I search for {string}")
    public void iSearchFor(String searchText) {
        world.homePage
                .searchProduct(searchText);
    }

    @Then("the product list should contain {int} products")
    public void theProductListShouldContainProducts(int expectedCount) {
        world.homePage
                .verifyProductCount(expectedCount);
    }

    @Then("the matching product list is displayed")
    public void theMatchingProductListIsDisplayed() {
        world.homePage
                .verifyProductsDisplayed();
    }
}
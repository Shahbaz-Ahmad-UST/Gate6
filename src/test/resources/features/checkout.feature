Feature: Checkout feature

    As a shopper
    I want to search products and add to cart
    so that I can place order successfully


  Background:
    Given I am on the login page

  @smoke @ui
  Scenario: Successful checkout

    When I login with valid credentials
    And I search for product "Metro Carryall"
    And I add the first product to the cart
    And I open the cart
    And I proceed to checkout
    And I enter the shipping address "Chennai, Tamil Nadu"
    And I place the order
    Then the order confirmation page is displayed
    And the order status should be "PLACED"
    And the order total should be displayed
    And the URL should contain the generated order number
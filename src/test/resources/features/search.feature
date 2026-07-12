Feature: Catalog search

  As a shopper
  I want to search products by SKU
  So that I can see the matching product list

  Background:
    Given the catalog is open

  @smoke @ui
  Scenario Outline: Search products by SKU
    When I search for "<searchText>"
    Then the product list should contain <count> products

    Examples:
      | searchText | count |
      | SKU        | 8     |
      | SKU-CAP    | 1     |
      | SKU-PEN    | 1     |
      | SKU-BTL    | 1     |
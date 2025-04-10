Feature: Product API

  @smoke
  Scenario: Retrieve product list with default pagination
    Given the product catalog contains products
    When I request the product list
    Then the response status should be 200
    And the response should contain 10 products

  @filter
  Scenario: Filter products by category
    Given the product catalog contains products
    When I search for products in category "Electronics"
    Then the response status should be 200
    And all products should have category "Electronics"

  @validation
  Scenario: Invalid pagination parameters
    When I request the product list with "page" "-1"
    Then the response status should be 400
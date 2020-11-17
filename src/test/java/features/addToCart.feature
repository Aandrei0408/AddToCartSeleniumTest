Feature:  Add to cart
  As a visitor of the FashionDays website
  I want to have a add to cart feature
  So that I can see the products added in the cart

  Scenario: Add two items ordered by the highest price to a cart
    Given I am on website home page
    And I select some category
    And I sort items by the highest price
    And I select the first item
    And I click the button to add to cart
    And I navigate back to previous page
    And I select the second item
    And I add the second item to cart
    When I go to Cart
    Then I should see both products added to cart
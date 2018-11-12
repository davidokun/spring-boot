Feature: Create a new Game
  As I user
  I want the ability to create a new game in the system.

  Scenario: Create Game
    Given An endpoint "/games"
    And a game with name "Zelda" and year published 1998
    And id is null
    And createdOn is null
    When user send a request to "/games"
    Then response should be wit status 201
    And id is greater than 0
    And createOn is not null
    And name is "Zelda"
    And year is 1998
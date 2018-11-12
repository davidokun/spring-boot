Feature: Search by games
  As a user
  I want to search games by different parameters

  Scenario Outline: Search Game by Id
    Given an endpoint "<endpoint>"
    And a game created with name "<name>"
    And year published "<year>"
    When user sends a GET request with parameter "<id>"
    Then a status code "<code>" is received
    And content type is "application/json;charset=UTF-8"
    And game name is "<name>"
    And year published is "<year>"

    Examples:
      |endpoint     | id |  name    | year  | code |
      |/games       | 1  |  Zelda   | 1998  | 200  |

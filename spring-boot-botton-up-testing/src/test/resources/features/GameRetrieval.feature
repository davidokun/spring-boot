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
      |/games       | 2  |  Asterix   | 2001  | 200  |

  Scenario Outline: Search All Games
    Given an endpoint for all games "<endpoint>"
    When I send a GET request
    Then I get a status "<status>"
    And a game with name "<name>"
    And year published is equal to "<year>"
    And id greater than "<id>"

    Examples:
      |endpoint     | id |  name      | year  | status  |
      |/games       | 0  |  Zelda     | 1998  | 200     |
      |/games       | 0  |  Asterix   | 2001  | 200     |
      |/games       | 0  |  Mario     | 1995  | 200     |

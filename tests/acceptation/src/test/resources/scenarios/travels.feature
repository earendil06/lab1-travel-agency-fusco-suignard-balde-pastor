Feature: Travels

  Background:
    Given the service deployed on localhost:9070

  Scenario: retrieving travels
    When retrieving pending requests
    Then the answer have 0 results

  Scenario: Sending a travel
    When propose a travel by email@email.com
    When retrieving pending requests
    Then the answer have 1 result
    And email@email.com have one pending request
    Then the answer have 1 result

#  Scenario: Get all pending Requests by uuidRequest
#    When propose a travel by email@email.com
#    When retrieving pending requests by uid 62b0566f-3d7a-430a-ad34-a66e6ee0baec
#    Then the answer have 0 result

  Scenario: Confirming a travel
    When propose a travel by email@email.com
    When retrieving pending requests
    Then the answer have 1 result
    When confirming this travel
    When retrieving pending requests
    Then the answer have 0 result
    When retrieving confirm requests
    Then the answer have 1 result
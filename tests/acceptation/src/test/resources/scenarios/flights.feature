Feature: Flights

  Background:
    Given an empty registry deployed on localhost:9080
    And a direct flight from Paris to London for 150 euros the 10/10/2017-20:00 duration 120 min
    And a non-direct flight from Paris to London for 100 euros the 11/10/2017-20:00 duration 120 min


  Scenario: Requiring all flights
    When the RETRIEVE message is sent
    Then the answer contains 2 results

  Scenario: Requiring all flights for a date
    When the parameter date is set as 10/10/2017
    Then the answers have as date the value 10/10/2017
    And the answer contains 1 result


  Scenario: Requiring all flights from a place to an other place
    When we require all flights from Paris to London
    Then all the results go from Paris to London
    And the answer contains 2 results



#  Scenario: Requiring all flights and order them
#  Scenario: Requiring only direct flights
#  Scenario: Requiring flights with max travel time
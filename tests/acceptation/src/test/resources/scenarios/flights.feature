Feature: Flights

  Background:
    Given an empty registry deployed on localhost:9080
    And a direct flight from Paris to London for 150.1 euros the 10/10/2017-20:00 duration 130 min
    And a non-direct flight from Paris to London for 149.8 euros the 09/10/2018-20:00 duration 100 min


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

  Scenario: Requiring all flights and order them
    When the RETRIEVE message is sent with option order by price
    Then all the results are sorted by price


  Scenario: Requiring only direct flights
    When the RETRIEVE message is sent with option only direct
    Then all the results only direct
    And the answer contains 1 result


  Scenario: Requiring flights with a max travel time
    When we require all flights with a 120 max duration
    Then all the results are less than 120 min
    And the answer contains 1 results
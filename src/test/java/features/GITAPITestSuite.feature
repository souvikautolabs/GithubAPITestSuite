Feature: GIT API Tests

  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description
    Given I am an user with <Keywords> willing to search repositories
    When I set mandatory fields for repository search
    And I send the request
    Then I should receive response code 200
    And I should be able to <ValidationType> with relevant <Keywords>

    Examples:
      |ValidationType | Keywords |
      | searchText |java+selenium+cucumber+jenkins+in:description |
      | sort | java+selenium+cucumber+jenkins+in:description sort:forks&order=desc|
##  java+selenium+cucumber+jenkins+in:description stars:<1000&order=desc

  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description and created after certain date
    Given I am an user with <Keywords> willing to search repositories
    When I set mandatory fields for repository search
    And I send the request
    Then I should receive response code 200
    And I should be able to verify that created date is after 2018-01-01

    Examples:
      | Keywords |
      |java+selenium+cucumber+in:description created:>2018-01-01 |


  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description and the size between a specified limit
    Given I am an user with <Keywords> willing to search repositories
    When I set mandatory fields for repository search
    And I send the request
    Then I should receive response code 200
    And I should be able to verify repository size between 100-1000

    Examples:
      | Keywords |
      | java+selenium+cucumber in:description size:100..1000 |

  Scenario Outline: As an user I want to search for GIT repositories that uses specific language and has star count more than 100 order by desc
    Given I am an user with <Keywords> willing to search repositories
    When I set mandatory fields for repository search
    And I send the request
    Then I should receive response code 200
    And I should be able to verify language and star count order

    Examples:
      | Keywords |
      | language:cobol sort:stars&order:desc stars:>100 |

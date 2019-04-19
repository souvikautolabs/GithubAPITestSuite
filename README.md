# GithubAPITestSuite

Libraries and Plugin Used:

Cucumber-jvm (https://cucumber.io/docs/reference/jvm#java)

Maven (https://maven.apache.org/)

Restassured (https://github.com/rest-assured/rest-assured/wiki/GettingStarted)

Pre-requisite:

Clone the project

Components:

a) Cucumber scripts - features/GITAPITestSuite.feature

  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description
		
  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description and created after certain date
		
  Scenario Outline: As an user I want to search for GIT repositories that has certain keywords in description and the size between a specified limit
  
  Scenario Outline: As an user I want to search for GIT repositories that uses specific language and has star count more than 100 order by desc
	

b) Test Runner - runner/GITAPITestRunner

c) API Test Framework - common/api

d) Utils - common/util

e) POJO classes - /model

f) Zomato API methods - /zomatoapis

g) Step definitions: Contains implementations of cucumber steps, interacts with business flows

Run:

Clone/download the project and run command from project directory - mvn test
Reports:

Default cucumber report will be generated in target/cucumber-reports

Feature: LoginFeature
  This feature deals with the job application in labcorp carrier portal

  Scenario: Applying Job in Labcorp carriers page
    Given I navigate to the labcorp home page
    And I click carriers link
    And I am on carriers page
    When I search for <JobTitle>
    |JobTitle                    |
		|QA Test Automation Developer|
    Then I should see <JobTitle> <JobLocation> and <JobID>
		|JobTitle                    |JobLocation                                       |JobID      |
		|QA Test Automation Developer| Durham, North Carolina, United States of America |21-90223_RM|
		And I should see "The right candidate for this role will participate in the test automation technology development and best practice models" in the description
		And I should see "Prepare test plans, budgets, and schedules.		" in the Management Support
		And also I should see "5+ years of experience in QA automation development and scripting." in the requirements section
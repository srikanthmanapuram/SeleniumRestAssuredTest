Feature: RestAssuredTestFeature
  This feature deals with the login functionality of the applications

	Background: User generates token for Authorisation
		Given I am an authorized user

	Scenario: the Authorized user can Add employee and retrive.
		Given A list of employes are available		
		When I add a employee to my directory list		
		Then the employee is added		
		When I get a employee from my directory list		
		Then the book is retrived
    


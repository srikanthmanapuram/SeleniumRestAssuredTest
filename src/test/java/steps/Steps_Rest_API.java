package steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Steps_Rest_API {
	private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
	private static final String USERNAME = "TOOLSQA-Test";
	private static final String PASSWORD = "Test@@123";
	private static final String BASE_URL = "https://6143a99bc5b553001717d06a.mockapi.io";

	private static String token;
	private static Response response;
	private static String jsonString;
	private static String bookId;

	@Given("I am an authorized user")
	public void iAmAnAuthorizedUser() {

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");
		response = request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
				.post("/Account/v1/GenerateToken");

		String jsonString = response.asString();
		token = JsonPath.from(jsonString).get("token");

	}

	@Given("A list of employes are available")
	public void listOfBooksAreAvailable() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/testapi/v1//Users");

		jsonString = response.asString();
		List<Map<String, String>> employees = JsonPath.from(jsonString).get("employee_firstname");
		Assert.assertTrue(employees.size() > 0);
	}

	@When("I add a employee to my directory list")
	public void addBookInList() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");

		response = request.body("{\r\n" + "        \"createdAt\": 1631825833,\r\n"
				+ "        \"employee_firstname\": \"TestData12345\",\r\n"
				+ "        \"employee_lastname\": \"TestData12345\",\r\n"
				+ "        \"employee_phonenumbe\": \"264-783-9453\",\r\n"
				+ "        \"ademployee_emaildress\": \"ademployee_emaildress 1\",\r\n"
				+ "        \"citemployee_addressy\": \"citemployee_addressy 1\",\r\n"
				+ "        \"stateemployee_dev_level\": \"stateemployee_dev_level 1\",\r\n"
				+ "        \"employee_gender\": \"employee_gender 1\",\r\n"
				+ "        \"employee_hire_date\": \"2025-10-31T16:35:45.426Z\",\r\n"
				+ "        \"employee_onleave\": true,\r\n" + "        \"tech_stack\": [],\r\n"
				+ "        \"project\": []\r\n" + "    }\r\n" + "").post("/testapi/v1//Users");
	}

	@Then("the employee is added")
	public void bookIsAdded() {
		Assert.assertEquals(201, response.getStatusCode());
	}

	@When("I get a employee from my directory list")
	public void removeBookFromList() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/testapi/v1//Users");

	}

	@Then("the book is retrived")
	public void bookIsRemoved() {
		jsonString = response.asString();
		List<Map<String, String>> employees = JsonPath.from(jsonString).get("employee_firstname");
		Assert.assertTrue(employees.size() > 0);
		Assert.assertEquals(200, response.getStatusCode());
	}
}
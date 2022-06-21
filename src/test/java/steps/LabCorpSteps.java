package steps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class LabCorpSteps {
	public static WebDriver driver;
	String originalWindow;

	@SuppressWarnings("deprecation")
	@Given("I navigate to the labcorp home page")
	public void i_navigate_to_the_labcorp_home_page() {
		// Create a new instance of the Firefox driver
		driver = new FirefoxDriver();
		// Put a Implicit wait, this means that any search for elements on the page
		// could take the time the implicit wait is set for before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Launch the Online Store Website
		driver.get("www.labcorp.com");
	}

	@Given("I click carriers link")
	public void i_click_carriers_link() {
		// Store the ID of the original window
		originalWindow = driver.getWindowHandle();

		// Check we don't have other windows open already
		assert driver.getWindowHandles().size() == 1;

		// Click the link which opens in a new window
		driver.findElement(By.xpath("//div[@id='login-container']//a[@class='no-ext ext']")).click();
	}

	@Given("I am on carriers page")
	public void i_am_on_carriers_page() {

		// Loop through until we find a new window handle
		for (String windowHandle : driver.getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
		// Wait for the new tab to finish loading content
		driver.getTitle().equalsIgnoreCase("Careers at Labcorp | Join The Pursuit");
	}

	@When("I search for <JobTitle>")
	public void i_search_for_job_title(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		driver.findElement(By.xpath(
				"//div[@class='ph-global-search-v3-default-theme9 ph-widget-target au-target']//form[@class='phs-global-search-area phs-global-sr-container ph-widget-box ph-mobile-search-backdrop au-target']//div[@class='form-group phs-global-search ph-search-group']//div[@class='au-target input-group dropdown-close']//div[@class='job-filter ph-suggestion-focus-block ph-search-group']//div//input[@id='typehead']"))
				.sendKeys(data.get(0).get(0));
		driver.findElement(By.xpath("//li[@id='typehead-result-item-0']//em[contains(text(),'Automation')]")).click();
	}

	@Then("I should see <JobTitle> <JobLocation> and <JobID>")
	public void i_should_see_job_title_job_location_and_job_id(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		String jobTitle=  driver.findElement(By.xpath("//h1[@class='job-title']")).getText();
		String jobLocation= driver.findElement(By.xpath("//span[@class='au-target job-location']")).getText();
		String jobDescription=driver.findElement(By.xpath("//span[@class='au-target jobId']")).getText();
		Assert.assertEquals(jobTitle,data.get(0).get(0));
		Assert.assertEquals(jobTitle,data.get(0).get(1));
		Assert.assertEquals(jobTitle,data.get(0).get(2));
	}

	@Then("I should see {string} in the description")
	public void i_should_see_in_the_description(String string) {
		String s1 = driver.findElement(By.xpath("//p[contains(text(),'Advance your Information Technology (IT) career at')]")).getText();
		Assert.assertEquals(s1, string);
	}

	@Then("I should see {string} in the Management Support")
	public void i_should_see_in_the_management_support(String string) {
		String s2 = driver.findElement(By.xpath("//p[normalize-space()='Prepare test plans, budgets, and schedules.']")).getText();
		Assert.assertEquals(s2, string);
	}

	@Then("also I should see {string} in the requirements section")
	public void also_i_should_see_in_the_requirements_section(String string) {
		String s3 = driver.findElement(By.xpath("//p[contains(text(),'5+ years of experience in QA automation developmen')]")).getText();
		Assert.assertEquals(s3, string);
	}

}

package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signUpUser() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,10);
		// Test sign up page.
		driver.get("http://localhost:"+this.port+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName"))).sendKeys("FName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName"))).sendKeys("LName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("selenium_test");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("12345..");
		driver.findElement(By.xpath("//button[@type='submit' or text()='Sign Up']")).click();
		// Test login page.
		Assertions.assertEquals("Login", driver.getTitle());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))).sendKeys("selenium_test");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))).sendKeys("12345..");
		driver.findElement(By.xpath("//button[@type='submit' or text()='Login']")).click();
		// Test if home page is opened after sign up and login.
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(5000);
	}

}

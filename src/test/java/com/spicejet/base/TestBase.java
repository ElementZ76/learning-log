package com.spicejet.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Properties prop;
	
	//method to initialize the config file
	public TestBase() {
		try {
			Properties prop = new Properties();
			FileInputStream ip = new FileInputStream("src/test/resources/config.properties");
			prop.load(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method to launch browser
	public void initialization() {
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome")) {
			driver = new ChromeDriver();
		}
		else if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		}
		else if(browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("write the browsername properly!");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
	}
	
	//wait for element to be visible
	public void waitForVisibility(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	//wait for element to be clickable
	public void waitForClickability(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		wait.ignoring(StaleElementReferenceException.class);
		waitForVisibility(element);
	}
	
	//method to retry clicking the element incase the element does not load. use attempts
	//prevention of stale element
	public void clickOn(WebElement element) {
		int attempts = 0;
		while(attempts<3) {
			try {
				waitForClickability(element);
				element.click();
				break;
			} catch (StaleElementReferenceException e) {
				attempts++;
				System.out.println("Element was stale. Retrying...");
			} catch (Exception e) {
				System.out.println("Max attempts reaced.");
			}
		}
	}
	
	//method to enter text in input field with attempts to prevent stale element exception
	public void sendText(WebElement element, String text) {
		int attempts = 0;
		while(attempts<3) {
			try {
				waitForVisibility(element);
				element.click();
				element.sendKeys(Keys.CONTROL + "a");
				element.sendKeys(Keys.BACK_SPACE);
				element.sendKeys(text);
				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("Element wsa stale. Retrying...");
			} catch(Exception e) {
				System.out.println("Max attempts reached.");
			}
		}
	}
	
	//method 
}

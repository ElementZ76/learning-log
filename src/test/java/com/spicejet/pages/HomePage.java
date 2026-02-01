package com.spicejet.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.spicejet.base.TestBase;

public class HomePage extends TestBase {
	FlightsPage flightsPage;
	
	@FindBy(xpath = "//div[normalize-space()='From']/following-sibling::div//input")
	WebElement fromBtn;
	
	@FindBy(xpath = "//div[normalize-space()='To']/following-sibling::div//input")
	WebElement toBtn;
	
	@FindBy(xpath = "//div[@data-testid='undefined-calendar-picker']")
	WebElement calendarContainer;
	
	@FindBy(xpath = "//div[contains(text(), 'Currency')]")
	WebElement currencyDropdown;
	
	@FindBy(xpath = "//div[contains(text(), '1 Adult')]")
	WebElement passengersDropdown;
	
	@FindBy(css = "div[data-testid='Adult-testID-plus-one-cta']")
	WebElement adultPlusBtn;
	
	@FindBy(css = "div[data-testid='Children-testID-plus-one-cta']")
	WebElement childrenPlusBtn;
	
	@FindBy(css = "div[data-testid='Infant-testID-plus-one-cta']")
	WebElement infantPlusBtn;
	
	@FindBy(xpath = "//div[@data-testid='home-page-flight-cta']") 
	WebElement searchFlightBtn;
	
	@FindBy(xpath = "//div[@data-testid='Flights-horizontal-nav-tabs']")
	WebElement flightsBtn;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void selectDate(Integer date) {
//		if(date.equalsIgnoreCase("Today")) {
//			date = String.valueOf(java.time.LocalDate.now().getDayOfMonth());
//		}
		
		String dynamicXpath = "//div[@data-testid='undefined-calendar-picker']" +
                "//div[contains(@class, 'css-76zvg2') and text()='" + date + "' " +
                "and not(contains(@class, 'disabled'))]";
		
//		try {
//			waitForVisibility(calendarContainer);
//			log.info("Calendar is visible");
//			WebElement dateToClick = driver.findElement(By.xpath(dynamicXpath));
//			dateToClick.click();
//			log.info("selected date:" + date);
//			Actions actions = new Actions(driver);
//			actions.sendKeys(Keys.ESCAPE).perform();
//			log.info("Pressed ESC to close calendar");
//			// Wait for the date picker to close after selecting the date
//			invisibilityOfElement(calendarContainer);
//			log.info("Date picker closed successfully");
//		} catch (Exception e) {
//			log.error("Error selecting date: " + e.getMessage());
//			throw new RuntimeException("Failed to select date", e);
//		}
//	}
		
		
		try {
			// Wait for calendar to be visible first
			waitForVisibility(calendarContainer);
			log.info("Calendar is visible");
			
			// Find and click the date
			WebElement dateToClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
			dateToClick.click();
			log.info("Selected date: " + date);
			
			// CRITICAL FIX: For one-way trips, SpiceJet calendar doesn't auto-close
			// We need to manually close it by clicking outside or pressing ESC
			
			// Method 1: Press ESC key to close calendar
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ESCAPE).perform();
			log.info("Pressed ENTER to close calendar");
			
			// Wait a bit for calendar to close
			Thread.sleep(800);
			
			// Verify calendar is closed (with reduced timeout since we forced it)
			try {
				wait.until(ExpectedConditions.invisibilityOf(calendarContainer));
				log.info("Date picker closed successfully");
			} catch (Exception e) {
				// If ESC didn't work, try clicking on passengers dropdown to force close
				log.warn("ESC didn't close calendar, trying to click elsewhere");
				flightsBtn.click();
				Thread.sleep(500);
				log.info("Closed calendar by clicking fligthsbtn");
			}
			
		} catch (NoSuchElementException e) {
			log.error("Date " + date + " not found in current month");
			throw e;
		} catch (Exception e) {
			log.error("Error selecting date: " + e.getMessage());
			throw new RuntimeException("Failed to select date", e);
		}
}
	
	public void selectPassengerCount(String adults, String children, String infants) {
		waitForClickability(passengersDropdown);
		passengersDropdown.click();
		log.info("Opened passenger dropdown");
		try {
			int adultCountClicks = Integer.parseInt(adults) - 1;
			int childCountClicks = Integer.parseInt(children);
			int infantCountClicks = Integer.parseInt(infants);
			
			for(int i = 0; i<adultCountClicks; i++) {
				waitForClickability(adultPlusBtn);
				adultPlusBtn.click();
			}
			
			log.info("Added " + adultCountClicks + " additional adults");
			
			for(int i = 0; i<childCountClicks; i++) {
				waitForClickability(childrenPlusBtn);
				childrenPlusBtn.click();
			}
			
			log.info("Added " + childCountClicks + " children");
			
			for(int i = 0; i<infantCountClicks; i++) {
				waitForClickability(infantPlusBtn);
				infantPlusBtn.click();
			}
			
			log.info("Added '" + infantCountClicks + "' infants");
			
		} catch (NoSuchElementException e) {
			log.error("Error selecting passengers");
			throw e;
		}
	}
	
	public void selectCurrency(String currency) {
		waitForClickability(currencyDropdown);
		currencyDropdown.click();
		log.info("Clicked currecny dropdown");
		String dynamicXpath = "//div[text()='" + currency + "']";
		
		try {
			WebElement currencyOption = driver.findElement(By.xpath(dynamicXpath));
			waitForClickability(currencyOption);
			currencyOption.click();
			log.info("Selected currency:" + currency);
		} catch (NoSuchElementException e) {
			log.error("currecny '" + currency + "' not found.");
			throw e;
		}
	}
	
	// method to keyboard select airports
	private void selectFromAirpotKeyboard(WebElement inputField, String airportCode, String fieldName) {
		try {
			//upgrade below method to using sendtext once i confirm this works
			waitForClickability(inputField);
			inputField.click();
			log.info("Clicked" + fieldName + "field");
			inputField.clear();
			inputField.sendKeys(airportCode);
			log.info("Typed" + fieldName + ": " + airportCode);
			Thread.sleep(1500);
//			inputField.sendKeys(Keys.ARROW_DOWN);
//			inputField.sendKeys(Keys.ENTER);
//			inputField.sendKeys(Keys.ENTER);
			log.info("Selected " + fieldName + " using keyboard navigation: " + airportCode);
		} catch (InterruptedException e) {
			        log.error("Thread interrupted while selecting " + fieldName);
			        Thread.currentThread().interrupt();
			        throw new RuntimeException("Selection interrupted", e);
			    } catch (Exception e) {
			        log.error("Failed to select " + fieldName + ": " + e.getMessage());
			        throw new RuntimeException("Failed to select " + fieldName, e);
			    }
}
	
	private void selectToAirpotKeyboard(WebElement inputField, String airportCode, String fieldName) {
		try {
			//upgrade below method to using sendtext once i confirm this works
			waitForClickability(inputField);
//			inputField.click();
//			log.info("Clicked" + fieldName + "field");
//			inputField.clear();
			inputField.sendKeys(airportCode);
			log.info("Typed" + fieldName + ": " + airportCode);
			Thread.sleep(1500);
//			inputField.sendKeys(Keys.ARROW_DOWN);
//			inputField.sendKeys(Keys.ENTER);
//			inputField.sendKeys(Keys.ENTER);
			log.info("Selected " + fieldName + " using keyboard navigation: " + airportCode);
//		} catch (InterruptedException e) {
//			        log.error("Thread interrupted while selecting " + fieldName);
//			        Thread.currentThread().interrupt();
//			        throw new RuntimeException("Selection interrupted", e);
			    } catch (Exception e) {
			        log.error("Failed to select " + fieldName + ": " + e.getMessage());
			        throw new RuntimeException("Failed to select " + fieldName, e);
			    }
}
	
	
	public FlightsPage searchForFlights(String origin, String destination, Integer date, String currency, String adults, String children, String infants) {
		try {
			log.info("Selecting origin");
			selectFromAirpotKeyboard(fromBtn, origin, "Origin");
		} catch (Exception e) {
			log.info("keyboard method failed");
		}
		
		try {
			log.info("Selecting destination");
			selectToAirpotKeyboard(toBtn, destination, "Destination");
		} catch (Exception e) {
			log.info("keyboard method failed");
		}
	    
		selectDate(date);
		selectPassengerCount(adults, children, infants);
		selectCurrency(currency);
		
		waitForClickability(searchFlightBtn); 
	    searchFlightBtn.click();
	    log.info("Clicked search flights button");
		return new FlightsPage();
	}
	
}

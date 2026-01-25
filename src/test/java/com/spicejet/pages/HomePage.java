package com.spicejet.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.spicejet.base.TestBase;

public class HomePage extends TestBase {
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
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void selectDate(String date) {
		if(date.equalsIgnoreCase("Today")) {
			date = String.valueOf(java.time.LocalDate.now().getDayOfMonth());
		}
		
		String dynamicXpath = "//div[contains(@data-testid, 'undefined-calendar-day') and contains(text(), '" + date + "')]";
		
		try {
			WebElement dateToClick = driver.findElement(By.xpath(dynamicXpath));
			dateToClick.click();
			log.info("selected date:" + date);
		} catch (NoSuchElementException e) {
			log.error("date" + date + "not found in current month");
			throw e;
		}
	}
	
	public void selectPassengerCount(String adults, String children, String infants) {
		waitForClickability(passengersDropdown);
		passengersDropdown.click();
		try {
			int adultCountClicks = Integer.parseInt(adults) - 1;
			int childCountClicks = Integer.parseInt(children);
			int infantCountClicks = Integer.parseInt(infants);
			
			for(int i = 0; i<adultCountClicks; i++) {
				waitForClickability(adultPlusBtn);
				adultPlusBtn.click();
			}
			
			for(int i = 0; i<childCountClicks; i++) {
				waitForClickability(childrenPlusBtn);
				childrenPlusBtn.click();
			}
			
			for(int i = 0; i<infantCountClicks; i++) {
				waitForClickability(infantPlusBtn);
				infantPlusBtn.click();
			}
			
//			log.info("Added '" + infantCount + "' infants");
			
		} catch (NoSuchElementException e) {
			log.error("Could not find element");
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
	
	public void searchForFlights(String origin, String destination, String date, String currency, String adults, String children, String infants) {
		sendText(fromBtn, origin);
		WebElement originOption = driver.findElement(By.xpath("//div[contains(text(), '" + origin + "')]"));
	    waitForClickability(originOption);
	    originOption.click();
		sendText(toBtn, destination);
		WebElement destinationOption = driver.findElement(By.xpath("//div[contains(text(), '" + destination + "')]"));
	    waitForClickability(destinationOption);
	    destinationOption.click();
		selectDate(date);
		selectPassengerCount(adults, children, infants);
		selectCurrency(currency);
	}
}

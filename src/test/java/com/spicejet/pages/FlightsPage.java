package com.spicejet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.spicejet.base.TestBase;

public class FlightsPage extends TestBase{
	@FindBy(xpath = "//div[contains(normalize-space(),'Select your')]")
	WebElement searchPageHeader;
	
	public FlightsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void isFlightHeaderDisplayed() throws InterruptedException {
		try {
			waitForVisibility(searchPageHeader);
			log.info("Search page header visible!");
		} catch (Exception e) {
			log.error("Searh page error not visible");
			throw e;
		}
	}
}

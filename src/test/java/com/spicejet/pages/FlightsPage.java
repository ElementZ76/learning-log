package com.spicejet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.spicejet.base.TestBase;

public class FlightsPage extends TestBase{
	@FindBy(xpath = "//div[@data-testid='search-page-header']")
	WebElement searchPageHeader;
	
	public FlightsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean isFlightHeaderDisplayed() {
		return driver.getCurrentUrl().contains("Search");
	}
}

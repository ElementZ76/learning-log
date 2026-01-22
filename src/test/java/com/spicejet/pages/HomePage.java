package com.spicejet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.spicejet.base.TestBase;

public class HomePage extends TestBase {
	@FindBy(xpath = "//div[normalize-space()='From']/follwing-sibling::div//input")
	WebElement fromBtn;
	
	@FindBy(xpath = "//div[normalize-space()='To']/follwing-sibling::div//input")
	WebElement toBtn;
	
	@FindBy(xpath = "//div[normalize-space()='Departure Date']/following-sibling::div//div")
	WebElement departureDate;
	
}

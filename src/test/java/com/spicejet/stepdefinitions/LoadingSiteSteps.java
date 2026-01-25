package com.spicejet.stepdefinitions;

import com.spicejet.base.TestBase;

import io.cucumber.java.en.Given;

public class LoadingSiteSteps extends TestBase{
	@Given("I launch the application")
	public void i_launch_the_application() {
		String url = prop.getProperty("url");
		driver.get(url);
		log.info("Navigating to URL: " + prop.getProperty("url"));
	}
}

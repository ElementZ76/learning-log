package com.spicejet.stepdefinitions;

import com.spicejet.base.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ApplicationHooks extends TestBase {
	
	@Before
	public void launchBrowser() {
		initialization();
	}
	
	@After
	public void quitBrowser() {
		tearDown();
	}

}

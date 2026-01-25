package com.spicejet.stepdefinitions;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;

import com.spicejet.base.TestBase;
import com.spicejet.models.SearchInfo;
import com.spicejet.pages.FlightsPage;
import com.spicejet.pages.HomePage;
import com.spicejet.utils.JsonUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SpicejetStepDef extends TestBase{
	
	HomePage homePage = new HomePage();
	FlightsPage flightsPage;
	
//	@When("I handle the {string} or popup if it appears")
//	public void i_handle_the_popup() {
//		System.out.println("okok");
//	}
	
	@When("I search for flights using details from {string}")
	public void i_search_for_flights_using_details_from(String fileName) throws IOException {
		List<SearchInfo> searchInfo = JsonUtils.getSearchInfo(fileName);
		for(SearchInfo info : searchInfo) {
			flightsPage = homePage.searchForFlights(
					info.getOrigin(), 
					info.getDestination(), 
					info.getDepartureDate(), 
					info.getCurrency(), 
					info.getAdults(), 
					info.getChildren(), 
					info.getInfants());
		}
	}
	
	@Then ("I should be navigated to the flight selection page") 
	public void i_should_be_navigated_to_the_flight_selection_page() {
		Assert.assertTrue(flightsPage.isFlightHeaderDisplayed(), "Flight Search Page was not displayed!");
	}
}

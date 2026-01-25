Feature: SpiceJet Flight Booking Automation

  Background:
    Given I launch the application

  @JsonDriven
  Scenario: Search for a One-Way Flight using JSON Data
    # 1. The Challenge: Handle the random bot/iframe before interacting
    # When I handle the "SpiceBot" or popup if it appears
    
    # 2. The Core Logic: Use the JSON data for the complex form
    When  I search for flights using details from "flightSearch.json"
    
    # 3. Validation: Verify we moved to the next page
    Then I should be navigated to the flight selection page
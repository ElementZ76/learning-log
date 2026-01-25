package com.spicejet.models;

public class SearchInfo {
    private String origin;
    private String destination;
    private String departureDate;
    private String currency;

    private String adults;
    private String children;
    private String infants;

    public SearchInfo() {}

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDepartureDate() { return departureDate; }
    public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAdults() { return adults; }
    public void setAdults(String adults) { this.adults = adults; }

    public String getChildren() { return children; }
    public void setChildren(String children) { this.children = children; }

    public String getInfants() { return infants; }
    public void setInfants(String infants) { this.infants = infants; }
}
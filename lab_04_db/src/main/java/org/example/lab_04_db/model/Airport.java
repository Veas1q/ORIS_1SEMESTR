package org.example.lab_04_db.model;

public class Airport {
    private String airportCode;
    private String airportName;
    private String city;

    public Airport() {}

    public Airport(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }

    // Геттеры и сеттеры
    public String getAirportCode() { return airportCode; }
    public void setAirportCode(String airportCode) { this.airportCode = airportCode; }

    public String getAirportName() { return airportName; }
    public void setAirportName(String airportName) { this.airportName = airportName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return airportName + " (" + airportCode + ") - " + city;
    }
}
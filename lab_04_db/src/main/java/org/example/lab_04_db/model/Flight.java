package org.example.lab_04_db.model;

import java.time.LocalDateTime;

public class Flight {
    private String flightNo;
    private String city;
    private String airportName;
    private String airportCode;
    private String status;
    private String type;
    private LocalDateTime time;

    public Flight() {};

    public Flight(String flightNo, String city, String airportName, String airportCode, String status, String type, LocalDateTime time) {
        this.flightNo = flightNo;
        this.city = city;
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.status = status;
        this.type = type;
        this.time = time;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

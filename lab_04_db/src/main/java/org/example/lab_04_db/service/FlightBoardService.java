package org.example.lab_04_db.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.lab_04_db.model.Airport;
import org.example.lab_04_db.model.Flight;
import org.example.lab_04_db.repository.AirportRepository;
import org.example.lab_04_db.repository.FlightRepository;

import java.time.LocalDate;
import java.util.List;

public class FlightBoardService {
    private FlightRepository flightRepository = new FlightRepository();
    private AirportRepository airportRepository = new AirportRepository();

    public String getAirportName(String airportCode) {
        Airport airport = airportRepository.findByCode(airportCode);
        return airport != null ? airport.getAirportName() : "Аэропорт " + airportCode;
    }

    public List<Flight> findFlights(String airportCode, String boardType, LocalDate date) {
        return flightRepository.findFlights(airportCode, boardType, date);
    }
}
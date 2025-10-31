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

    public void processRequest(HttpServletRequest request) {
        List<Airport> airports = airportRepository.findAllAirports();
        request.setAttribute("airports", airports);

        String airportCode = request.getParameter("airport");
        String boardType = request.getParameter("type");
        String dateStr = request.getParameter("date");

        if (airportCode == null || airportCode.isEmpty()) {
            airportCode = "LED";
        }
        if (boardType == null || boardType.isEmpty()) {
            boardType = "departure";
        }

        LocalDate date;
        if (dateStr == null || dateStr.isEmpty()) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateStr);
        }

        List<Flight> flights = flightRepository.findFlights(airportCode, boardType, date);

        String airportName = getAirportName(airportCode);

        request.setAttribute("flights", flights);
        request.setAttribute("currentAirport", airportCode);
        request.setAttribute("currentAirportName", airportName);
        request.setAttribute("boardType", boardType);
        request.setAttribute("selectedDate", date.toString());
    }

    private String getAirportName(String airportCode) {
        Airport airport = airportRepository.findByCode(airportCode);
        return airport != null ? airport.getAirportName() : "Аэропорт " + airportCode;
    }
}
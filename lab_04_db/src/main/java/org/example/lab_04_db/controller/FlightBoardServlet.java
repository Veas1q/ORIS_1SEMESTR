package org.example.lab_04_db.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab_04_db.model.Airport;
import org.example.lab_04_db.model.Flight;
import org.example.lab_04_db.service.AirplaneService;
import org.example.lab_04_db.service.FlightBoardService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/board")
public class FlightBoardServlet extends HttpServlet {
    private FlightBoardService flightBoardService = new FlightBoardService();
    private AirplaneService airplaneService = new AirplaneService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Airport> airports = airplaneService.findAllAirports();
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

        List<Flight> flights = flightBoardService.findFlights(airportCode, boardType, date);

        String airportName = flightBoardService.getAirportName(airportCode);

        request.setAttribute("flights", flights);
        request.setAttribute("currentAirport", airportCode);
        request.setAttribute("currentAirportName", airportName);
        request.setAttribute("boardType", boardType);
        request.setAttribute("selectedDate", date.toString());

        // Показываем страницу
        request.getRequestDispatcher("/board.ftlh").forward(request, response);
    }
}
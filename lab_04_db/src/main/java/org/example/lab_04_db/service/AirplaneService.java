package org.example.lab_04_db.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.lab_04_db.model.Airplane;
import org.example.lab_04_db.model.Airport;
import org.example.lab_04_db.repository.AirplaneRepository;
import org.example.lab_04_db.repository.AirportRepository;

import java.util.List;

public class AirplaneService {

    private AirplaneRepository repository = new AirplaneRepository();
    private AirportRepository airportRepository = new AirportRepository();

    public void fillAttributes(HttpServletRequest request) {
        List<Airplane> airplanes = repository.findAll();
        request.setAttribute("airplanes", airplanes);
    }

    public List<Airport> findAllAirports() {
        List<Airport> airports = airportRepository.findAllAirports();
        return  airports;
    }
}
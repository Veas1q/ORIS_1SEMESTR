package org.example.lab_04_db.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.lab_04_db.model.Airplane;
import org.example.lab_04_db.repository.AirplaneRepository;

import java.util.ArrayList;
import java.util.List;

public class AirplaneService {

    private AirplaneRepository repository = new AirplaneRepository();

    public void fillAttributes(HttpServletRequest request) {
        List<Airplane> airplanes = repository.findAll();
        request.setAttribute("airplanes", airplanes);
    }
}
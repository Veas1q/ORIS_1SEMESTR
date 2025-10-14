package org.example.lab_04_db.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab_04_db.repository.AirplaneRepository;
import org.example.lab_04_db.service.AirplaneService;

import java.io.IOException;

@WebServlet("/airplanes")
public class AirplanesServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AirplanesServlet.class);

    private AirplaneService airplaneService = new AirplaneService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        airplaneService.fillAttributes(request);
        request.getRequestDispatcher("C:\\Users\\Redmi\\IdeaProject\\ORIS_1SEMESTR\\lab_04_db\\src\\main\\resources\\resources\\template\\airplanes.ftlh")
                .forward(request, response);
    }

}
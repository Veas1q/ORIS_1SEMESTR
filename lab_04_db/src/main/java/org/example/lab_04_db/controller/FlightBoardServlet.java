package org.example.lab_04_db.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab_04_db.service.FlightBoardService;

import java.io.IOException;

@WebServlet("/board")
public class FlightBoardServlet extends HttpServlet {
    private FlightBoardService flightBoardService = new FlightBoardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Обрабатываем запрос через сервис
        flightBoardService.processRequest(request);

        // Показываем страницу
        request.getRequestDispatcher("/board.ftlh").forward(request, response);
    }
}
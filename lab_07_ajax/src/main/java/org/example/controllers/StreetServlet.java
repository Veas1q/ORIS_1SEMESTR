package org.example.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.Street;
import org.example.repository.StreetRepository;

import java.io.IOException;
import java.util.List;
@WebServlet("/Street")
public class StreetServlet extends HttpServlet {
    private StreetRepository streetRepository;

    public void init() {
        ServletContext servletContext = getServletContext();
        streetRepository = (StreetRepository) servletContext.getAttribute("streetRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("city"));
            List<Street> streets = streetRepository.getAllByCityId(id);
            StringBuilder json = new StringBuilder("{\"streets\":[);
        }
    }
}

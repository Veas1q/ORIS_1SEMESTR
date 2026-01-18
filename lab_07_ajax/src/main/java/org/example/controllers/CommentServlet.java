package org.example.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.City;
import org.example.repository.CityRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(CommentServlet.class);

    private CityRepository cityRepository;

    public void init() {
        ServletContext context = getServletContext();
        cityRepository =
                (CityRepository) context.getAttribute("cityRepository");
        logger.debug("cityRepository = " + cityRepository);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            City city = cityRepository.getById(Long.parseLong(request.getParameter("cityId")));
            if (city!=null) {
                response.getWriter().write(city.getComment());
            }
        } catch (Exception e) {
            logger.atError().withThrowable(e).log();
        }

    }
}
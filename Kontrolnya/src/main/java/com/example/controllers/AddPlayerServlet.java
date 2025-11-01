package com.example.controllers;


import com.example.models.Player;
import com.example.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add")
public class AddPlayerServlet extends HttpServlet {
    private UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add-player.ftlh").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Player player = new Player();
            player.setNickname(request.getParameter("nickname"));
            player.setName(request.getParameter("name"));
            player.setGame(request.getParameter("game"));
            player.setTeam(request.getParameter("team"));
            player.setActive(request.getParameter("isActive") != null);

            double earnings = 0;
            try {
                earnings = Double.parseDouble(request.getParameter("earnings"));
            } catch (NumberFormatException e) {
                earnings = 0;
            }
            player.setEarnings(earnings);

            player.setCountry(request.getParameter("country"));


            userService.addPlayer(player);


            response.sendRedirect("/show");

        } catch (SQLException e) {
            request.setAttribute("error", "Ошибка при добавлении: " + e.getMessage());
            request.getRequestDispatcher("/add-player.ftlh").forward(request, response);
        }
    }
}
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
import java.util.List;

@WebServlet("/showone")
public class ShowOnePlayerServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");

        if (nickname == null || nickname.trim().isEmpty()) {
            request.setAttribute("error", "Никнейм не указан");
            request.getRequestDispatcher("/search-player.ftlh").forward(request, response);
            return;
        }

        try {
            List<Player> players = userService.findByNickname(nickname);

            if (players.isEmpty()) {
                request.setAttribute("error", "Игрок с ником '" + nickname + "' не найден");
                request.getRequestDispatcher("/search-player.ftlh").forward(request, response);
            } else {
                request.setAttribute("player", players.get(0));
                request.getRequestDispatcher("/show-one-player.ftlh").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
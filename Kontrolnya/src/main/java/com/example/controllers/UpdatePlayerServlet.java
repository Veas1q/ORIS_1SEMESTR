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

@WebServlet("/update")
public class UpdatePlayerServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oldNickname = request.getParameter("oldNickname");
        String nickname = request.getParameter("nickname");
        String name = request.getParameter("name");
        String game = request.getParameter("game");
        String team = request.getParameter("team");
        boolean isActive = request.getParameter("isActive") != null;

        double earnings = 0;
        try {
            earnings = Double.parseDouble(request.getParameter("earnings"));
        } catch (NumberFormatException e) {
            earnings = 0;
        }

        String country = request.getParameter("country");


        Player player = new Player();
        player.setNickname(nickname);
        player.setName(name);
        player.setGame(game);
        player.setTeam(team);
        player.setActive(isActive);
        player.setEarnings(earnings);
        player.setCountry(country);

        try {

            userService.updatePlayer(player, oldNickname);

            response.sendRedirect("show");

        } catch (SQLException e) {
            request.setAttribute("error", "Ошибка при обновлении: " + e.getMessage());
            request.getRequestDispatcher("/show-one-player.ftlh").forward(request, response);
        }
    }
}
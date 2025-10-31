package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(UserCheckServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String resource;

        // Если сессия существует и пользователь уже залогинен — сразу на index
        if (session != null && session.getAttribute("user") != null) {
            resource = "/index.ftlh";
        } else {
            // Проверяем логин/пароль
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if ("admin".equals(username) && "admin".equals(password)) {
                session = request.getSession(true);
                session.setAttribute("user", username);
                resource = "/index.ftlh";
            } else {
                request.setAttribute("errormessage", "Неверное имя пользователя или пароль!");
                resource = "/login.ftlh";
            }
        }

        request.getRequestDispatcher(resource).forward(request, response);
    }
}
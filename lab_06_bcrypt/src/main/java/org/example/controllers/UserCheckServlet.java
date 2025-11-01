package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.User;
import org.example.services.UserService;

import java.io.IOException;

@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(UserCheckServlet.class);

    private UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        String resource = "/index.ftlh";

        if (session == null || session.getAttribute("user") == null) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // select id, username, password from users where username = ? ;

            try {
                User user = userService.validateUser(username, password);
                if (user != null) {
                    session = request.getSession(true);
                    session.setAttribute("user", user);
                    resource = "/index.ftlh";

                } else {
                    request.setAttribute("errormessage", "Неверное имя пользователя или пароль!");
                    resource = "/login.ftlh";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        request.getRequestDispatcher(resource)
                .forward(request, response);
    }

}
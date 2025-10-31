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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(RegistrationServlet.class);
    private UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.ftlh")
                .forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setHashPassword(request.getParameter("password"));
        user.setLastName(request.getParameter("lastname"));
        user.setFirstName(request.getParameter("firstname"));
        user.setPhone(request.getParameter("phone"));

        try {
            userService.addUser(user);
        } catch (Exception e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("/registration.ftlh")
                    .forward(request, response);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        request.setAttribute("user", user.getLastName());
        request.getRequestDispatcher("/index.ftlh")
                .forward(request, response);
    }
}
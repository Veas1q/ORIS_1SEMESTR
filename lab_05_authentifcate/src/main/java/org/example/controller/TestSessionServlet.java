package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/session")
public class TestSessionServlet extends HttpServlet{
    final static Logger logger = LogManager.getLogger(TestSessionServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ищется кука,
        //ищется ассоциированная с ней сессия (объект HttpSession)
        //если не находится - создается новый (если не указан флаг false)
       HttpSession session = request.getSession(false);
       String resource = "/index.ftlh";
       if (session == null || session.getAttribute("user") == null) {
           String username = request.getParameter("username");
           String password = request.getParameter("password");
           if (username.equals("admin") || password.equals("admin")) {
               session.setAttribute("user", username);
               resource = "index.ftlh";
           }else {
               request.setAttribute("errormessage", "Неверное имя пользователя или пароль");
               resource = "login.ftlh";
           }
       }

        request.getRequestDispatcher("login.ftlh").forward(request, response);
    }

}
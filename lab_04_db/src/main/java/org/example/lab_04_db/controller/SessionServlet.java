package org.example.lab_04_db.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/truesession")
public class SessionServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(SessionServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ищется кука
        // ищется ассоциированная с ней сессия (объект HttpSession)
        // если не находится - создается новый (если не указан флаг false)
        HttpSession session = request.getSession(false);
        String username = "инкогнито"; // значение по умолчанию
        if (session == null) {
            session = request.getSession(true);
            String userParam = request.getParameter("user");
            if (userParam != null && !userParam.trim().isEmpty()) {
                username = userParam;
                session.setAttribute("username", username);
            }
        } else {
            String sessionUsername = (String) session.getAttribute("username");
            if (sessionUsername != null) {
                username = sessionUsername;
            }
        }


        request.setAttribute("sessionId", session.getId());
        request.setAttribute("username", username);

        request.getRequestDispatcher("/session.ftlh")
                .forward(request, response);
    }

}
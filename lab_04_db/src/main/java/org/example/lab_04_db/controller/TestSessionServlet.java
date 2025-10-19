package org.example.lab_04_db.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/session")
public class TestSessionServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(TestSessionServlet.class);
    boolean flag = false;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                request.setAttribute("sessionId", cookie.getValue());
                flag = true;
                break;
            }
        }
        if (!flag) {
            request.setAttribute("sessionId", "JSESSIONID не нашли");
            Cookie cookie = new Cookie("JSESSIONID", "123");
            response.addCookie(cookie);
        }



        request.getRequestDispatcher("session.ftlh").forward(request, response);
        }

}
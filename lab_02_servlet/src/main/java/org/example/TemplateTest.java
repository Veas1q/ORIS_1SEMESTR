package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

@WebServlet("/template/test")
public class TemplateTest extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TemplateTest.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("default", "Александр");

        // Обрабатываем логику приложения
        // Отрисовка страницы - передаем дальше request

        req.getRequestDispatcher("/test.html").forward(req, resp);
    }
}

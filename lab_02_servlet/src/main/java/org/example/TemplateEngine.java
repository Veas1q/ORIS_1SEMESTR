package org.example;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(value = "*.html")
public class TemplateEngine  extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TemplateEngine.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());

        Map<String, String> params = new HashMap<>();
        Iterator<String> enumeration = req.getAttributeNames().asIterator();
    
        while (enumeration.hasNext()) {
            String attributeName = enumeration.next();
            String attributeValue = req.getAttribute(attributeName).toString();
            logger.debug(attributeName + " : " + attributeValue);
            params.put(attributeName, attributeValue);
        }


        String fileName = req.getServletPath().substring(1);
        URL url = TemplateEngine.class.getClassLoader().getResource("templates/" + fileName);

        String template = null;
        try {
            template = Files.readString(Paths.get(url.toURI()));
            TemplateHandler templateHandler = new TemplateHandler();
            //встройка шаблонизатора в TemplateEngine
            templateHandler.handle(fileName, params, resp.getWriter());
        } catch (URISyntaxException e) {
            logger.error("Error reading template file: " + e.getMessage());
            resp.sendError(500, "Internal server error");
        }


    }
}

package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException, IOException {
        String protocol = servletRequest.getProtocol();
        String remoteAddr = servletRequest.getRemoteAddr();
        int remotePort = servletRequest.getRemotePort();
        Map<String, String[]> parameters = servletRequest.getParameterMap();
        Map<String, String> params = new HashMap<>();
        params.put("protocol", protocol);
        params.put("remoteAddr", remoteAddr);
        params.put("remotePort", remotePort+"");
        if(parameters.get("username") == null){
            params.put("username","Александр");
        }else {
            params.put("username", (parameters.get("username")[0]));
        }
        new TemplateHandler().handle("Shablon.html", params, servletResponse.getWriter());


    }
}

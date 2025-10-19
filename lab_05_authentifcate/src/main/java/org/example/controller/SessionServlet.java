//package org.example.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.example.lab_04_db.controller.TestSessionServlet;
//
//import java.io.IOException;
//
//@WebServlet("/truesession")
//public class SessionServlet extends HttpServlet {
//    final static Logger logger = LogManager.getLogger(TestSessionServlet.class);
//
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //ищется кука
//        //ищется ассоциированная с ней сессия (объект HttpSession)
//        //если не находится - создается новый (если не указан флаг false)
//       HttpSession session = request.getSession(false);
//       if (session == null){
//           session = request.getSession(true);
//           session.setAttribute("username", request.getParameter("user"));
//            request.setAttribute("username", "инкогнито");
//       }else {
//           request.setAttribute("username", session.getAttribute("username"));
//       }
//
//        request.getRequestDispatcher("login.ftlh").forward(request, response);
//    }
//
//}
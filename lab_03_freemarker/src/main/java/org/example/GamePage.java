package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/game")
public class GamePage extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GamePage.class);

    private final Map<String, GameState> gamers = new Hashtable<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());

        String uid = UUID.randomUUID().toString();
        GameState gameState = new GameState();
        gamers.put(uid, gameState);
        req.setAttribute("table", gameState.getTable());
        req.setAttribute("uuid", uid);



        req.getRequestDispatcher("/game.ftlh").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String row = req.getParameter("row");
        String col = req.getParameter("col");
        String uid = req.getParameter("uid");
        logger.debug("uid: " + uid, "row: " + row, "col: " + col);


        GameState gameState = gamers.get(uid);
        List<Row> table = gameState.getTable();
        Row trow = table.get(Integer.parseInt(row) - 1);
        trow.setT("k.jpg");

        req.setAttribute("table", gameState.getTable());
        req.setAttribute("uuid", uid);



        req.getRequestDispatcher("/game.ftlh").forward(req, resp);
    }
}

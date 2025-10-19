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
        req.setAttribute("uid", uid);


        req.getRequestDispatcher("/game.ftlh").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String row = request.getParameter("row");
        String column = request.getParameter("col");
        String uid = request.getParameter("uid");

        logger.debug("uid " + uid + ", row " + row + ", col " + column);

        GameState gameState = gamers.get(uid);

        List<Row> table = gameState.getTable();
        Row trow = new Row();

        if (Integer.parseInt(column) >= 0&& Integer.parseInt(column) <= 3) {
            trow = table.get(Integer.parseInt(row) - 1);
        }else {
            System.out.println("Цена не соответствует ни одному из значений");
        }

        switch (column) {
            case "1":
                trow.setF("k.png");
                break;
            case "2":
                trow.setS("k.png");
                break;
            case "3":
                trow.setT("k.png");
                break;
            default:
                break;
        }

        request.setAttribute("table", table);
        request.setAttribute("uid", uid);

        request.getRequestDispatcher("/game.ftlh")
                .forward(request, response);
    }
}

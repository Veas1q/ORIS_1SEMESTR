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
    private final BotPlayer botPlayer = new BotPlayer();

    private static final Logger logger = LogManager.getLogger(GamePage.class);
    private final Map<String, GameState> gamers = new Hashtable<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getServletPath());

        String uid = UUID.randomUUID().toString();
        GameState gameState = new GameState();
        gamers.put(uid, gameState);

        // Проверяем, началась ли игра
        String start = req.getParameter("start");
        boolean gameStarted = "true".equals(start);

        if (gameStarted && gameState.getCurrentPlayer().equals("O")) {
            botPlayer.makeMove(gameState);
            gameState.switchPlayer();
        }

        req.setAttribute("table", gameState.getTable());
        req.setAttribute("uid", uid);
        req.setAttribute("currentPlayer", gameState.getCurrentPlayer());
        req.setAttribute("winner", gameState.getWinner());
        req.setAttribute("gameStarted", gameStarted);

        req.getRequestDispatcher("/game.ftlh").forward(req, resp);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String row = request.getParameter("row");
        String column = request.getParameter("col");
        String uid = request.getParameter("uid");

        GameState gameState = gamers.get(uid);

        //  ПРОВЕРКА: если игра уже завершена (победой или ничьей)
        if (gameState.getWinner() != null) {
            request.setAttribute("table", gameState.getTable());
            request.setAttribute("uid", uid);
            request.setAttribute("winner", gameState.getWinner());
            request.getRequestDispatcher("/game.ftlh").forward(request, response);
            return;
        }

        // Проверка занятости клетки
        List<Row> table = gameState.getTable();
        int rowIndex = Integer.parseInt(row) - 1;
        int colIndex = Integer.parseInt(column) - 1;

        String currentCellValue = getCellValue(table, rowIndex, colIndex);

        if (!currentCellValue.equals("p.png")) {
            request.setAttribute("error", "Эта клетка уже занята!");
            request.setAttribute("table", gameState.getTable());
            request.setAttribute("uid", uid);
            request.setAttribute("currentPlayer", gameState.getCurrentPlayer());
            request.getRequestDispatcher("/game.ftlh").forward(request, response);
            return;
        }

        // Ход человека
        Row trow = table.get(rowIndex);
        switch (column) {
            case "1": trow.setF("k.png"); break;
            case "2": trow.setS("k.png"); break;
            case "3": trow.setT("k.png"); break;
        }

        //  ПРОВЕРКА ПОБЕДЫ ИЛИ НИЧЬИ ПОСЛЕ ХОДА ЧЕЛОВЕКА
        String winner = gameState.checkStatusGame();
        if (winner != null) {
            gameState.setWinner(winner); // есть победитель
        } else if (gameState.isDraw()) {
            gameState.setWinner("DRAW"); //  ничья
        } else {
            // Переключаем игрока
            gameState.switchPlayer();

            // Ход бота
            if (gameState.getCurrentPlayer().equals("O") && gameState.getWinner() == null) {
                botPlayer.makeMove(gameState);

                //  ПРОВЕРКА ПОБЕДЫ ИЛИ НИЧЬИ ПОСЛЕ ХОДА БОТА
                winner = gameState.checkStatusGame();
                if (winner != null) {
                    gameState.setWinner(winner);
                } else if (gameState.isDraw()) {
                    gameState.setWinner("DRAW"); //  ничья
                } else {
                    gameState.switchPlayer(); // возвращаем ход человеку
                }
            }
        }

        request.setAttribute("table", gameState.getTable());
        request.setAttribute("uid", uid);
        request.setAttribute("winner", gameState.getWinner());
        request.setAttribute("currentPlayer", gameState.getCurrentPlayer());
        request.getRequestDispatcher("/game.ftlh").forward(request, response);
    }

    //  Метод для получения значения клетки
    private String getCellValue(List<Row> table, int row, int col) {
        Row currentRow = table.get(row);
        return switch (col) {
            case 0 -> currentRow.getF();
            case 1 -> currentRow.getS();
            case 2 -> currentRow.getT();
            default -> "p.png";
        };
    }
}
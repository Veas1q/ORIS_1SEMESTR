package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotPlayer {

    public void makeMove(GameState gameState) {
        List<Row> table = gameState.getTable();

        //  СТРАТЕГИЯ 1: Попробовать выиграть
        String winningMove = findWinningMove(table, "o.png");
        if (winningMove != null) {
            makeMoveFromString(table, winningMove, "o.png");
            return;
        }

        //  СТРАТЕГИЯ 2: Блокировать победу противника
        String blockingMove = findWinningMove(table, "k.png");
        if (blockingMove != null) {
            makeMoveFromString(table, blockingMove, "o.png");
            return;
        }

        //  СТРАТЕГИЯ 3: Занять центр если свободен
        if (getCellValue(table, 1, 1).equals("p.png")) {
            setCellValue(table, 1, 1, "o.png");
            return;
        }

        //  СТРАТЕГИЯ 4: Случайный ход из оставшихся
        List<String> freeCells = getFreeCells(table);
        if (!freeCells.isEmpty()) {
            Random random = new Random();
            String randomCell = freeCells.get(random.nextInt(freeCells.size()));
            makeMoveFromString(table, randomCell, "o.png");
        }
    }

    //  Найти выигрышный ход для указанного игрока
    private String findWinningMove(List<Row> table, String playerSymbol) {
        List<String> freeCells = getFreeCells(table);

        for (String cell : freeCells) {
            // Пробуем поставить символ в свободную клетку
            makeMoveFromString(table, cell, playerSymbol);

            // Проверяем победу
            if (checkWinForPlayer(table, playerSymbol)) {
                // Отменяем пробный ход и возвращаем эту клетку
                makeMoveFromString(table, cell, "p.png");
                return cell;
            }

            // Отменяем пробный ход
            makeMoveFromString(table, cell, "p.png");
        }
        return null;
    }

    //  Проверить победу для конкретного игрока
    private boolean checkWinForPlayer(List<Row> table, String playerSymbol) {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            Row row = table.get(i);
            board[i][0] = row.getF();
            board[i][1] = row.getS();
            board[i][2] = row.getT();
        }

        int[][] winLines = {
                {0,0, 0,1, 0,2}, {1,0, 1,1, 1,2}, {2,0, 2,1, 2,2},
                {0,0, 1,0, 2,0}, {0,1, 1,1, 2,1}, {0,2, 1,2, 2,2},
                {0,0, 1,1, 2,2}, {0,2, 1,1, 2,0}
        };

        for (int[] line : winLines) {
            String cell1 = board[line[0]][line[1]];
            String cell2 = board[line[2]][line[3]];
            String cell3 = board[line[4]][line[5]];

            if (cell1.equals(playerSymbol) && cell2.equals(playerSymbol) && cell3.equals(playerSymbol)) {
                return true;
            }
        }
        return false;
    }

    //  Получить список свободных клеток в формате "row, col"
    private List<String> getFreeCells(List<Row> table) {
        List<String> freeCells = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (getCellValue(table, row, col).equals("p.png")) {
                    freeCells.add(row + "," + col);
                }
            }
        }
        return freeCells;
    }

    //  Сделать ход из строки "row, col"
    private void makeMoveFromString(List<Row> table, String cell, String symbol) {
        String[] parts = cell.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        setCellValue(table, row, col, symbol);
    }

    private String getCellValue(List<Row> table, int row, int col) {
        Row currentRow = table.get(row);
        return switch (col) {
            case 0 -> currentRow.getF();
            case 1 -> currentRow.getS();
            case 2 -> currentRow.getT();
            default -> "p.png";
        };
    }

    private void setCellValue(List<Row> table, int row, int col, String value) {
        Row currentRow = table.get(row);
        switch (col) {
            case 0 -> currentRow.setF(value);
            case 1 -> currentRow.setS(value);
            case 2 -> currentRow.setT(value);
        }
    }
}
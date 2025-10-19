package org.example;


import java.util.List;

public class GameState {
    private List<Row> table = List.of(new Row(), new Row(), new Row());
    private String winner = null; // "X", "O" или null если ничья/игра продолжается
    private String currentPlayer; // X - человек, O - бот
    public GameState() {
        //  Случайный выбор кто ходит первым
        this.currentPlayer = Math.random() < 0.5 ? "X" : "O";
    }

    public boolean isDraw() {
        // Проверяем, что нет победителя и все клетки заполнены
        if (checkStatusGame() != null) {
            return false; // есть победитель - не ничья
        }

        // Проверяем, что все клетки заполнены
        for (Row row : table) {
            if (row.getF().equals("p.png") || row.getS().equals("p.png") || row.getT().equals("p.png")) {
                return false; // есть пустая клетка - не ничья
            }
        }

        return true; // все клетки заполнены и нет победителя - ничья
    }

    public String checkStatusGame() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            Row row = table.get(i);
            board[i][0] = row.getF();
            board[i][1] = row.getS();
            board[i][2] = row.getT();
        }
        int[][] winLines = {
                {0,0, 0,1, 0,2}, {1,0, 1,1, 1,2}, {2,0, 2,1, 2,2}, // строки
                {0,0, 1,0, 2,0}, {0,1, 1,1, 2,1}, {0,2, 1,2, 2,2}, // столбцы
                {0,0, 1,1, 2,2}, {0,2, 1,1, 2,0}                  // диагонали
        };
        for (int[] line : winLines) {
            String cell1 = board[line[0]][line[1]];
            String cell2 = board[line[2]][line[3]];
            String cell3 = board[line[4]][line[5]];

            if (!cell1.equals("p.png") && cell1.equals(cell2) && cell2.equals(cell3)) {
                // Определяем кто выиграл по картинке
                return cell1.equals("k.png") ? "X" : "O";
            }
        }

        return null;
    };
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<Row> getTable() {
        return table;
    }

    public void setTable(List<Row> table) {
        this.table = table;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

}


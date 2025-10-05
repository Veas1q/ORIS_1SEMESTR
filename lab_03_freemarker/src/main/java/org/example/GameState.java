package org.example;


import java.util.List;

public class GameState {
    private Boolean gaveOver = false;
    private List<Row> table = List.of(new Row(), new Row(), new Row());

    public Boolean getGaveOver() {
        return gaveOver;
    }

    public void setGaveOver(Boolean gaveOver) {
        this.gaveOver = gaveOver;
    }

    public List<Row> getTable() {
        return table;
    }

    public void setTable(List<Row> table) {
        this.table = table;
    }
}


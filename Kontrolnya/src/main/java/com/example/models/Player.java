package com.example.models;

public class Player {
    private int id;
    private String nickname;
    private String team;
    private String name;
    private String game;
    private double earnings;
    private String country;
    private boolean isActive;

    public Player(){
    }

    public Player(int id, String nickname, String team, String name, String game, double earnings, String country, boolean isActive) {
        this.id = id;
        this.nickname = nickname;
        this.team = team;
        this.name = name;
        this.game = game;
        this.earnings = earnings;
        this.country = country;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

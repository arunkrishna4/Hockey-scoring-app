package com.example.hockey;

public class Player {
    private String name;
    private String jerseyNo;
    private String position;

    public Player(String name, String jerseyNo, String position) {
        this.name = name;
        this.jerseyNo = jerseyNo;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getJersey() {
        return jerseyNo;
    }

    public String getPosition() {
        return position;
    }
}

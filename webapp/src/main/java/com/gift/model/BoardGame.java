package com.gift.model;

public class BoardGame extends Game {
    private final int minPlayers;

    public BoardGame(String name, double weight, double price, int minPlayers) {
        super(name, weight, price);
        this.minPlayers = minPlayers;
    }

    public int getMinPlayers() { return minPlayers; }

    @Override
    public String getDescription() {
        return "Board Game (" + getBaseInfo() + ", Min Players: " + minPlayers + ")";
    }
}

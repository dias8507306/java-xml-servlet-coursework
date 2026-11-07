package com.gift.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** A New Year gift: a collection of games with weight / sort / search operations. */
public class Gift {
    private final List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    /** Calculates total weight. */
    public double calculateTotalWeight() {
        double totalWeight = 0;
        for (Game game : games) {
            totalWeight += game.getWeight();
        }
        return totalWeight;
    }

    /** Sorts the games with the given comparator. */
    public void sortGames(Comparator<Game> comparator) {
        games.sort(comparator);
    }

    /** Finds games whose weight is within [minWeight, maxWeight]. */
    public List<Game> findGamesByWeightRange(double minWeight, double maxWeight) {
        return games.stream()
                .filter(game -> game.getWeight() >= minWeight && game.getWeight() <= maxWeight)
                .collect(Collectors.toList());
    }

    public List<Game> getGames() {
        return games;
    }
}

package com.gift.model;

/** Base class of all gift items (same hierarchy as console/Main.java). */
public abstract class Game implements Comparable<Game> {
    private final String name;
    private final double weight;
    private final double price;

    public Game(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public double getPrice() { return price; }

    public String getBaseInfo() {
        return "Name: " + name + ", Weight: " + weight + " kg, Price: " + price + " KZT";
    }

    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }

    /** Default sorting (by weight). */
    @Override
    public int compareTo(Game other) {
        return Double.compare(this.weight, other.weight);
    }
}

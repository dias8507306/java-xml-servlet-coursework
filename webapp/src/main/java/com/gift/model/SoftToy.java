package com.gift.model;

public class SoftToy extends Game {
    private final String material;

    public SoftToy(String name, double weight, double price, String material) {
        super(name, weight, price);
        this.material = material;
    }

    public String getMaterial() { return material; }

    @Override
    public String getDescription() {
        return "Soft Toy (" + getBaseInfo() + ", Material: " + material + ")";
    }
}

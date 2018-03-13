package de.epex.pokerhands.service;

public enum Rank {
    HIGH_CARD(1, "High Card"),
    PAIR(2, "High Card"),
    TWO_PAIRS(3, "High Card"),
    THREE_OF_A_KIND(4, "High Card"),
    STRAIGHT(5, "High Card"),
    FLUSH(6, "High Card"),
    FULL_HOUSE(7, "High Card"),
    FOUR_OF_A_KIND(8, "High Card"),
    STRAIGHT_FLUSH(9, "High Card"),
    ROYAL_FLUSH(10, "High Card");

    private final int value;

    private final String name;

    Rank(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

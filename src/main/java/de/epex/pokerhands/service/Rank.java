package de.epex.pokerhands.service;

public enum Rank {
    HIGH_CARD(1, "High card"),
    PAIR(2, "Pair"),
    TWO_PAIRS(3, "Two pairs"),
    THREE_OF_A_KIND(4, "Three of a kind"),
    STRAIGHT(5, "Straight"),
    FLUSH(6, "Flush"),
    FULL_HOUSE(7, "Full house"),
    FOUR_OF_A_KIND(8, "Four of a kind"),
    STRAIGHT_FLUSH(9, "Straight flush"),
    ROYAL_FLUSH(10, "Royal flush");

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

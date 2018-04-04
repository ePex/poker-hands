package de.epex.pokerhands.service.model;

import java.util.Objects;

public class Card {

    private final String suite;

    private final int value;

    public Card(String cardString) {
        this(cardString, true);
    }

    Card(String cardString, boolean validate) {
        suite = getSuiteFromCardString(cardString);
        value = getValueFromCardString(cardString);
        if (validate && !Deck.isInDeck(this)) {
            throw new IllegalArgumentException(String.format("Card(%s) is not in deck", toString()));
        }
    }

    private String getSuiteFromCardString(String card) {
        return String.valueOf(card.charAt(0));
    }

    private int getValueFromCardString(String card) {
        String stringValue = card.substring(1).toUpperCase();

        switch (stringValue) {
            case "T":
                return 10;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            default:
                return Integer.valueOf(stringValue);
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", suite, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                Objects.equals(suite, card.suite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suite, value);
    }

    public String getSuite() {
        return suite;
    }

    public int getValue() {
        return value;
    }
}

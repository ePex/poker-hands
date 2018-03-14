package de.epex.pokerhands.service.model;

public class Card {

    private final String suite;

    private final int value;

    public Card(String cardString) {
        // TODO handle errors / wrong values
        suite = getSuiteFromCardString(cardString);
        value = getValueFromCardString(cardString);
    }

    private String getSuiteFromCardString(String card) {
        return String.valueOf(card.charAt(0));
    }

    private int getValueFromCardString(String card) {
        String stringValue = card.substring(1).toUpperCase();

        switch (stringValue) {
            case "J":
                return 10;
            case "Q":
                return 11;
            case "K":
                return 12;
            case "A":
                return 13;
            default:
                return Integer.valueOf(stringValue);
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", suite, value);
    }

    public String getSuite() {
        return suite;
    }

    public int getValue() {
        return value;
    }
}

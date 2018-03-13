package de.epex.pokerhands.service;

public class HandRanker {
    public Rank getRank(String hand) {
        String[] cards = hand.split("\\s");
        int highestCard = 2;
        for (String card : cards) {
            int value = getValue(card);
            if (value > highestCard) {
                highestCard = value;
            }
        }

        return Rank.HIGH_CARD;
    }

    private int getValue(String card) {
        int value;
        String stringValue = card.substring(1).toUpperCase();

        switch (stringValue) {
            case "J":
                value = 11;
                break;
            case "Q":
                value = 12;
                break;
            case "K":
                value = 13;
                break;
            case "A":
                value = 14;
                break;
            default:
                value = Integer.valueOf(stringValue);
                break;
        }

        return value;
    }
}

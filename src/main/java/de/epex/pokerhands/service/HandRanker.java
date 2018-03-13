package de.epex.pokerhands.service;

import java.util.*;
import java.util.stream.Collectors;

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

        int pairCount = getPairCount(cards);
        if (pairCount == 1) {
            return Rank.PAIR;
        }
        if (pairCount == 2) {
            return Rank.TWO_PAIRS;
        }

        return Rank.HIGH_CARD;
    }

    private int getPairCount(String[] cards) {
        List<Integer> strings = Arrays.stream(cards).map(this::getValue).collect(Collectors.toList());

        Set<Integer> allItems = new HashSet<>();
        Set<Integer> duplicates = strings.stream()
                .filter(n -> !allItems.add(n)) //Set.add() returns false if the item was already in the set.
                .collect(Collectors.toSet());

        return duplicates.size();
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

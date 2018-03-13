package de.epex.pokerhands.service;

import java.util.*;
import java.util.stream.Collectors;

public class HandRanker {
    public Rank getRank(String hand) {
        String[] cards = hand.split("\\s");
        List<Integer> values = Arrays.stream(cards).map(this::getValue).collect(Collectors.toList());

        int pairCount = getPairCount(values);
        if (pairCount == 1) {
            return Rank.PAIR;
        }
        if (pairCount == 2) {
            return Rank.TWO_PAIRS;
        }

        return Rank.HIGH_CARD;
    }

    private int getPairCount(List<Integer> cards) {
        Set<Integer> allItems = new HashSet<>();
        Set<Integer> duplicates = cards.stream()
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

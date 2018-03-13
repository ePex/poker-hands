package de.epex.pokerhands.service;

import java.util.*;
import java.util.stream.Collectors;

public class HandRanker {
    public Rank getRank(String hand) {
        String[] cards = hand.split("\\s");
        List<Integer> values = Arrays.stream(cards).map(this::getValue).collect(Collectors.toList());

        //return Rank.ROYAL_FLUSH;
        //return Rank.STRAIGHT_FLUSH;
        if (hasFourOfAKind(values)) {
            return Rank.FOUR_OF_A_KIND;
        }
        //return Rank.FULL_HOUSE;
        //return Rank.FLUSH;
        //return Rank.STRAIGHT;

        if (hasThreeOfAKind(values)) {
            return Rank.THREE_OF_A_KIND;
        }

        int pairCount = getPairCount(values);
        if (pairCount == 2) {
            return Rank.TWO_PAIRS;
        }
        if (pairCount == 1) {
            return Rank.PAIR;
        }

        return Rank.HIGH_CARD;
    }

    private boolean hasFourOfAKind(List<Integer> cards) {
        Set<Integer> uniqueSet = new HashSet<>(cards);
        for (Integer temp : uniqueSet) {
            if (Collections.frequency(cards, temp) == 4) {
                return true;
            }
        }

        return false;
    }

    private boolean hasThreeOfAKind(List<Integer> cards) {
        Set<Integer> uniqueSet = new HashSet<>(cards);
        for (Integer temp : uniqueSet) {
            if (Collections.frequency(cards, temp) == 3) {
                return true;
            }
        }

        return false;
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

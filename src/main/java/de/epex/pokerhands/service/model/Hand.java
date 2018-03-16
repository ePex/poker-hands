package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Hand {

    private static final int SIZE = 5;

    private List<Card> cards = new ArrayList<>();

    public Hand(String handString) {
        String[] cardsAsStrings = handString.split("\\s");

        if (invalidSize(cardsAsStrings.length)) {
            throw new IllegalArgumentException(String.format("Hand is either to small or big (must contain %d cards)", SIZE));
        }

        for (String cardString : cardsAsStrings) {
            cards.add(new Card(cardString));
        }
        sort();
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(card -> card + " ").collect(Collectors.joining());
    }

    public List<Card> getCards() {
        return cards;
    }

    private boolean invalidSize(int size) {
        return size < SIZE || size > SIZE;
    }

    private void sort() {
        cards.sort(Comparator.comparingInt(Card::getValue));
    }

    public Set<Integer> getPairs() {
        List<Integer> cards = getCards().stream().map(Card::getValue).collect(Collectors.toList());
        Set<Integer> allItems = new HashSet<>();

        return cards.stream()
                .filter(n -> !allItems.add(n)) //Set.add() returns false if the item was already in the set.
                .collect(Collectors.toSet());
    }
}

package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        StringBuilder result = new StringBuilder();
        for (Card card : cards) {
            result.append(card).append(" ");
        }

        return result.toString();
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

}

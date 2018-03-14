package de.epex.pokerhands.service.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {

    private static final int SIZE = 5;

    private List<Card> cards = new ArrayList<>();

    public Hand(String handString) {
        String[] split = handString.split("\\s");

        if (invalidSize(split.length)) {
            throw new IllegalArgumentException("Hand is either to small or big (must contain " + SIZE + " cards)");
        }

        for (String s : split) {
            cards.add(new Card(s));
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

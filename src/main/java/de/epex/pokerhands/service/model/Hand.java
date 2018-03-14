package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {

    private static final int SIZE = 5;

    private List<Card> cards = new ArrayList<>();

    public Hand(String handString) {
        String[] split = handString.split("\\s");
        // check size
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

    private void sort() {
        cards.sort(Comparator.comparingInt(Card::getValue));
    }

    public List<Card> getCards() {
        return cards;
    }

}

package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        List<String> suites = Arrays.asList("C", "D", "H", "S");
        List<String> values = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");

        //TODO refactor this to a stream operation
        for (String suite : suites) {
            for (String value : values) {
                cards.add(new Card(suite + value));
            }
        }
    }

    public boolean isInDeck(Card card) {
        return cards.stream().anyMatch(deckCard -> deckCard.toString().equalsIgnoreCase(card.toString()));
    }

    public int getDeckSize() {
        return cards.size();
    }
}

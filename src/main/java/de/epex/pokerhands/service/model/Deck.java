package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {

    private static final List<Card> cards = new ArrayList<>();

    static {
        List<String> suites = Arrays.asList("C", "D", "H", "S");
        List<String> values = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");

        suites.forEach(suite -> values.stream()
                .map(value -> new Card(suite + value, false))
                .forEach(cards::add)
        );
    }

    public static boolean isInDeck(Card card) {
        return cards.stream()
                .anyMatch(deckCard -> deckCard.toString().equalsIgnoreCase(card.toString()));
    }

    public static int getDeckSize() {
        return cards.size();
    }
}

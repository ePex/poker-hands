package de.epex.pokerhands.service.model; // Package remains the same for now

import de.epex.pokerhands.domain.Card; // Updated import for Card

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Deck {

    private static final List<Card> cards = new ArrayList<>();

    static {
        List<String> suites = Arrays.asList("C", "D", "H", "S");
        // Values 2-9, then T, J, Q, K, A
        List<String> valueStrings = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");

        for (String suite : suites) {
            for (String valueStr : valueStrings) {
                // Card constructor is now the record's canonical constructor
                cards.add(new Card(suite, convertValueStrToInt(valueStr)));
            }
        }
    }

    private static int convertValueStrToInt(String valueStr) {
        switch (valueStr.toUpperCase()) {
            case "T": return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 14;
            default: return Integer.parseInt(valueStr);
        }
    }

    public static boolean isInDeck(Card card) { // Parameter is domain.Card
        // Logic remains the same as it already uses record accessors
        return cards.stream()
                .anyMatch(deckCard -> deckCard.suite().equalsIgnoreCase(card.suite()) && deckCard.value() == card.value());
    }

    public static int getDeckSize() {
        return cards.size();
    }
}

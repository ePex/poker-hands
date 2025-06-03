package de.epex.pokerhands.service.model;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Hand {

    private static final int SIZE = 5;

    private final List<Card> cards;
    private transient Map<Integer, Long> cardsWithSameValueCache; // Added cache field

    public Hand(String handString) {
        if (handString == null || handString.trim().isEmpty()) {
            throw new InvalidPokerHandException("Hand string cannot be null or empty.");
        }
        String[] cardsAsStrings = handString.trim().split("\\s+");

        if (invalidSize(cardsAsStrings.length)) {
            throw new InvalidPokerHandException(String.format("Hand must contain exactly %d cards. Provided: %d. Input was: '%s'", SIZE, cardsAsStrings.length, handString));
        }

        List<Card> tempCards = new ArrayList<>();
        for (String cardString : cardsAsStrings) {
            try {
                tempCards.add(Card.fromString(cardString));
            } catch (InvalidPokerHandException e) {
                throw new InvalidPokerHandException(String.format("Invalid card in hand: '%s'. Error: %s", cardString, e.getMessage()), e);
            }
        }
        tempCards.sort(Comparator.comparingInt(Card::value));
        this.cards = List.copyOf(tempCards);
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(" "));
    }

    public List<Card> getCards() {
        return cards;
    }

    private boolean invalidSize(int size) {
        return size != SIZE;
    }

    public Map<Integer, Long> getCardsWithSameValue() {
        if (this.cardsWithSameValueCache == null) {
            Map<Integer, Long> cardValuesWithOccurrenceCount = cards.stream()
                    .collect(Collectors.groupingBy(Card::value, Collectors.counting()));

            this.cardsWithSameValueCache = cardValuesWithOccurrenceCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return this.cardsWithSameValueCache;
    }
}

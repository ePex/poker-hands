package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Hand {

    private static final int SIZE = 5;

    private List<Card> cards = new ArrayList<>();

    public Hand(String handString) {
        String[] cardsAsStrings = handString.split("\\s");

        if (invalidSize(cardsAsStrings.length)) {
            throw new IllegalArgumentException(String.format("Hand is either to small or big (must contain %d cards)", SIZE));
        }

        Arrays.stream(cardsAsStrings).forEach(cardString -> cards.add(new Card(cardString)));
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

    public Card getHighCard() {
        return cards.get(cards.size() - 1);
    }

    public boolean hasPair() {
        return getCardsWithSameValue().values().stream()
                .filter(cardValueOccurrenceCount -> cardValueOccurrenceCount == 2)
                .count() == 1;
    }

    public boolean hasTwoPair() {
        return getCardsWithSameValue().values().stream()
                .filter(cardValueOccurrenceCount -> cardValueOccurrenceCount == 2)
                .count() == 2;
    }

    public boolean hasThreeOfAKind() {
        return hasCountOfAKind(3);
    }

    public boolean hasStraight() {
        Card previousCard = null;
        int count = 0;
        for (Card card : cards) {
            if (previousCard == null || (previousCard.getValue() == card.getValue() - 1)) {
                previousCard = card;
                count++;
            } else {
                return false;
            }
        }

        return count == 5;
    }

    public boolean hasFlush() {
        String suite = null;
        for (Card card : cards) {
            if (suite == null || suite.equalsIgnoreCase(card.getSuite())) {
                suite = card.getSuite();
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean hasFullHouse() {
        return hasCountOfAKind(3) && hasCountOfAKind(2);
    }

    public boolean hasFourOfAKind() {
        return hasCountOfAKind(4);
    }

    public boolean hasStraightFlush() {
        return hasStraight() && hasFlush();
    }

    public boolean hasRoyalFlush() {
        Card referenceCard = new Card("DA");

        return hasStraightFlush() && referenceCard.getValue() == getHighCard().getValue();
    }


    private boolean hasCountOfAKind(int count) {
        List<Integer> cardValues = cards.stream().map(Card::getValue).collect(Collectors.toList());
        Set<Integer> uniqueSet = new HashSet<>(cardValues);

        return uniqueSet.stream().anyMatch(temp -> Collections.frequency(cardValues, temp) == count);
    }


    private boolean invalidSize(int size) {
        return size < SIZE || size > SIZE;
    }

    private void sort() {
        cards.sort(Comparator.comparingInt(Card::getValue));
    }

    public Map<Integer, Long> getCardsWithSameValue() {
        List<Integer> cards = getCards().stream().map(Card::getValue).collect(Collectors.toList());

        // count card occurrence
        Map<Integer, Long> cardValuesWithOccurrenceCount = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // reduce by all cards that occur only once and return new map
        return cardValuesWithOccurrenceCount.entrySet().stream()
                .filter(cardValueOccurrenceCount -> cardValueOccurrenceCount.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

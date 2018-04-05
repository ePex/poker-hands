package de.epex.pokerhands.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

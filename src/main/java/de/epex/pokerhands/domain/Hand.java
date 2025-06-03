package de.epex.pokerhands.domain;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hand {

    private static final int SIZE = 5;

    private final List<Card> cards; // Already sorted by value, low to high
    private transient Map<Integer, Long> cardsWithSameValueCache;
    private final HandEvaluationResult evaluationResult;

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

        // Add this check for distinct cards:
        if (tempCards.stream().distinct().count() != SIZE) {
            throw new InvalidPokerHandException("Hand must contain 5 distinct cards. Duplicates found in input: " + handString);
        }

        tempCards.sort(Comparator.comparingInt(Card::value));
        this.cards = List.copyOf(tempCards);
        this.evaluationResult = calculateEvaluation();
    }

    public HandEvaluationResult getEvaluationResult() {
        return this.evaluationResult;
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

    private Map<Integer, Long> getGroupedCardValues() {
        if (this.cardsWithSameValueCache == null) {
            Map<Integer, Long> allCardCounts = this.cards.stream()
                    .collect(Collectors.groupingBy(Card::value, Collectors.counting()));
            this.cardsWithSameValueCache = allCardCounts;
        }
        return this.cardsWithSameValueCache;
    }

    public Map<Integer, Long> getCardsInGroups() {
        return getGroupedCardValues().entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private HandEvaluationResult calculateEvaluation() {
        boolean flush = checkFlush();
        boolean straight = checkStraight();
        Map<Integer, Long> valueCounts = getGroupedCardValues();

        if (straight && flush) {
            boolean isAceLowSF = cards.get(4).value() == 14 && cards.get(0).value() == 2;
            int highCardValue = isAceLowSF ? 5 : cards.get(4).value();
            Rank rank = (cards.get(0).value() == 10 && cards.get(4).value() == 14 && !isAceLowSF) ? Rank.ROYAL_FLUSH : Rank.STRAIGHT_FLUSH; // Corrected Royal Flush condition slightly
            return new HandEvaluationResult(rank, List.of(highCardValue));
        }

        List<Integer> fourOfAKindValues = getValuesOfGroups(valueCounts, 4L);
        if (!fourOfAKindValues.isEmpty()) {
            int fourValue = fourOfAKindValues.get(0);
            List<Integer> kickers = getKickers(List.of(fourValue));
            return new HandEvaluationResult(Rank.FOUR_OF_A_KIND, Stream.concat(Stream.of(fourValue), kickers.stream()).collect(Collectors.toList()));
        }

        List<Integer> threeOfAKindValues = getValuesOfGroups(valueCounts, 3L);
        List<Integer> pairValues = getValuesOfGroups(valueCounts, 2L);

        if (!threeOfAKindValues.isEmpty() && !pairValues.isEmpty()) {
            return new HandEvaluationResult(Rank.FULL_HOUSE, List.of(threeOfAKindValues.get(0), pairValues.get(0)));
        }

        if (flush) {
            return new HandEvaluationResult(Rank.FLUSH, getSortedCardValuesDesc());
        }

        if (straight) {
            boolean isAceLow = cards.get(4).value() == 14 && cards.get(0).value() == 2;
            return new HandEvaluationResult(Rank.STRAIGHT, List.of(isAceLow ? 5 : cards.get(4).value()));
        }

        if (!threeOfAKindValues.isEmpty()) {
            int threeValue = threeOfAKindValues.get(0);
            List<Integer> kickers = getKickers(List.of(threeValue));
            return new HandEvaluationResult(Rank.THREE_OF_A_KIND, Stream.concat(Stream.of(threeValue), kickers.stream()).collect(Collectors.toList()));
        }

        if (pairValues.size() == 2) {
            int highPair = Math.max(pairValues.get(0), pairValues.get(1));
            int lowPair = Math.min(pairValues.get(0), pairValues.get(1));
            List<Integer> kickers = getKickers(List.of(highPair, lowPair));
            return new HandEvaluationResult(Rank.TWO_PAIRS, List.of(highPair, lowPair, kickers.get(0)));
        }

        if (pairValues.size() == 1) {
            int pairValue = pairValues.get(0);
            List<Integer> kickers = getKickers(List.of(pairValue));
            return new HandEvaluationResult(Rank.PAIR, Stream.concat(Stream.of(pairValue), kickers.stream()).collect(Collectors.toList()));
        }

        return new HandEvaluationResult(Rank.HIGH_CARD, getSortedCardValuesDesc());
    }

    private boolean checkFlush() {
        if (cards.isEmpty()) return false;
        String firstSuite = cards.get(0).suite();
        return cards.stream().allMatch(card -> card.suite().equals(firstSuite));
    }

    private boolean checkStraight() {
        if (cards.size() != 5) return false;
        boolean isAceLow = cards.get(0).value() == 2 &&
                           cards.get(1).value() == 3 &&
                           cards.get(2).value() == 4 &&
                           cards.get(3).value() == 5 &&
                           cards.get(4).value() == 14;
        if (isAceLow) return true;

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).value() + 1 != cards.get(i + 1).value()) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getValuesOfGroups(Map<Integer, Long> valueCounts, long groupSize) {
        return valueCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == groupSize)
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<Integer> getKickers(List<Integer> excludeValues) {
        return cards.stream()
                .map(Card::value)
                .filter(value -> !excludeValues.contains(value))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<Integer> getSortedCardValuesDesc() {
        return cards.stream()
                .map(Card::value)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}

package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Card;
import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.service.Rank;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Ranker {

    // Removed SA_STRING as it's no longer needed for isRoyalFlush

    public Rank getRank(Hand hand) {
        // It's generally more efficient to check for higher ranks first.
        // The order here seems mostly fine, but keep in mind for future optimizations.
        if (isRoyalFlush(hand)) {
            return Rank.ROYAL_FLUSH;
        }
        if (isStraightFlush(hand)) {
            return Rank.STRAIGHT_FLUSH;
        }
        if (isFourOfAKind(hand)) {
            return Rank.FOUR_OF_A_KIND;
        }
        if (isFullHouse(hand)) {
            return Rank.FULL_HOUSE;
        }
        if (isFlush(hand)) {
            return Rank.FLUSH;
        }
        if (isStraight(hand)) {
            return Rank.STRAIGHT;
        }
        if (isThreeOfAKind(hand)) {
            return Rank.THREE_OF_A_KIND;
        }
        if (isTwoPair(hand)) {
            return Rank.TWO_PAIRS;
        }
        if (isPair(hand)) {
            return Rank.PAIR;
        }

        return Rank.HIGH_CARD;
    }

    private boolean isRoyalFlush(Hand hand) {
        List<Card> cards = hand.getCards();
        // A Royal Flush is a Straight Flush from Ten to Ace.
        // Cards are sorted by value in Hand's constructor.
        return isStraightFlush(hand) &&
               cards.get(0).value() == 10 && // Ten
               cards.get(4).value() == 14;  // Ace
    }

    private boolean isStraightFlush(Hand hand) {
        // Both must be true. isFlush is generally cheaper to check first.
        return isFlush(hand) && isStraight(hand);
    }

    private boolean isFourOfAKind(Hand hand) {
        return hasCountOfAKind(hand, 4);
    }

    private boolean isFullHouse(Hand hand) {
        // Check if there's one pair and one three-of-a-kind.
        // The getCardsWithSameValue() map in Hand only contains groups with count > 1.
        // So, for a full house, this map should contain exactly two entries: one for the pair (count 2) and one for the triplet (count 3).
        Map<Integer, Long> counts = hand.getCardsWithSameValue();
        return counts.size() == 2 && counts.containsValue(2L) && counts.containsValue(3L);
    }

    private boolean isFlush(Hand hand) {
        List<Card> cards = hand.getCards();
        if (cards.isEmpty()) { // Should not happen with Hand's constructor validation
            return false;
        }
        String firstSuite = cards.get(0).suite();
        return cards.stream().allMatch(card -> card.suite().equals(firstSuite));
    }

    private boolean isStraight(Hand hand) {
        List<Card> cards = hand.getCards();
        // Hand constructor ensures there are 5 cards and they are sorted by value.

        // Check for Ace-low straight (A, 2, 3, 4, 5)
        // Sorted as 2, 3, 4, 5, A (where A has value 14)
        boolean isAceLowStraight = cards.get(0).value() == 2 &&
                                   cards.get(1).value() == 3 &&
                                   cards.get(2).value() == 4 &&
                                   cards.get(3).value() == 5 &&
                                   cards.get(4).value() == 14; // Ace
        if (isAceLowStraight) {
            return true;
        }

        // Check for standard straight (e.g., 5, 6, 7, 8, 9)
        boolean isStandardStraight = true;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).value() + 1 != cards.get(i + 1).value()) {
                isStandardStraight = false;
                break;
            }
        }
        return isStandardStraight;
    }

    private boolean isThreeOfAKind(Hand hand) {
        // Must be three of a kind and not a full house (which also has three of a kind).
        // The getCardsWithSameValue() map in Hand only contains groups with count > 1.
        // For three of a kind, this map should contain exactly one entry with count 3.
        Map<Integer, Long> counts = hand.getCardsWithSameValue();
        return counts.size() == 1 && counts.containsValue(3L);
    }

    private boolean hasCountOfAKind(Hand hand, int count) {
        // This helper is more general.
        // For specific ranks like FourOfAKind, ThreeOfAKind, Pair, TwoPair,
        // it's often better to use the size and specific counts from getCardsWithSameValue().
        Map<Integer, Long> counts = hand.getCardsWithSameValue();
        return counts.containsValue((long) count);
    }

    private boolean isTwoPair(Hand hand) {
        // The getCardsWithSameValue() map should contain exactly two entries, both with count 2.
        Map<Integer, Long> counts = hand.getCardsWithSameValue();
        return counts.size() == 2 && counts.values().stream().allMatch(count -> count == 2L);
    }

    private boolean isPair(Hand hand) {
        // The getCardsWithSameValue() map should contain exactly one entry with count 2.
        Map<Integer, Long> counts = hand.getCardsWithSameValue();
        return counts.size() == 1 && counts.containsValue(2L);
    }

    // getPairCount is no longer directly used by isTwoPair or isPair,
    // but could be kept if it's useful for other logic or future extensions.
    // For now, let's comment it out or remove it if not needed.
    // private int getPairCount(Hand hand) {
    //     Map<Integer, Long> cardsWithSameValue = hand.getCardsWithSameValue();
    //     return (int) cardsWithSameValue.values().stream()
    //             .filter(cardValueOccurrenceCount -> cardValueOccurrenceCount == 2)
    //             .count();
    // }

}

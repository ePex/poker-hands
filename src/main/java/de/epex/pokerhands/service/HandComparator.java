package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Card;
import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.service.Rank; // Corrected import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HandComparator implements Comparator<Hand> {

    private final Ranker ranker;

    @Autowired
    public HandComparator(Ranker ranker) {
        this.ranker = ranker;
    }

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return zero on draw negative value if first hand is less then second hand and positive value if first hand is higher then second hand
     */
    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Rank firstHandRank = ranker.getRank(firstHand);
        Rank secondHandRank = ranker.getRank(secondHand);

        if (firstHandRank != secondHandRank) {
            return Integer.compare(firstHandRank.getValue(), secondHandRank.getValue());
        }

        // Ranks are the same, apply tie-breaking rules
        switch (firstHandRank) {
            case ROYAL_FLUSH:
                return 0; // Royal flushes are always a tie
            case STRAIGHT_FLUSH:
            case FLUSH:
            case STRAIGHT:
            case HIGH_CARD:
                return breakTieByHighestCard(firstHand, secondHand);
            case FOUR_OF_A_KIND:
                return breakTieForFourOfAKind(firstHand, secondHand);
            case FULL_HOUSE:
                return breakTieForFullHouse(firstHand, secondHand);
            case THREE_OF_A_KIND:
                return breakTieForThreeOfAKind(firstHand, secondHand);
            case TWO_PAIRS:
                return breakTieForTwoPairs(firstHand, secondHand);
            case PAIR:
                return breakTieForPair(firstHand, secondHand);
            default:
                // Should not be reached if all ranks are handled
                return 0;
        }
    }

    private int breakTieByHighestCard(Hand firstHand, Hand secondHand) {
        // Cards are sorted by Ranker, iterate from highest to lowest
        for (int i = firstHand.getCards().size() - 1; i >= 0; i--) {
            int firstHandCardValue = firstHand.getCards().get(i).value();
            int secondHandCardValue = secondHand.getCards().get(i).value();
            if (firstHandCardValue != secondHandCardValue) {
                return Integer.compare(firstHandCardValue, secondHandCardValue);
            }
        }
        return 0; // All cards are the same
    }

    private int getHighestCardValueForCount(Hand hand, long count) {
        return hand.getCardsWithSameValue().entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .mapToInt(Map.Entry::getKey)
                .max()
                .orElse(0);
    }

    private int breakTieForFourOfAKind(Hand firstHand, Hand secondHand) {
        int firstHandFourValue = getHighestCardValueForCount(firstHand, 4);
        int secondHandFourValue = getHighestCardValueForCount(secondHand, 4);

        if (firstHandFourValue != secondHandFourValue) {
            return Integer.compare(firstHandFourValue, secondHandFourValue);
        }
        // Compare kicker
        return breakTieByHighestCard(firstHand, secondHand);
    }

    private int breakTieForFullHouse(Hand firstHand, Hand secondHand) {
        int firstHandThreeValue = getHighestCardValueForCount(firstHand, 3);
        int secondHandThreeValue = getHighestCardValueForCount(secondHand, 3);

        if (firstHandThreeValue != secondHandThreeValue) {
            return Integer.compare(firstHandThreeValue, secondHandThreeValue);
        }

        int firstHandPairValue = getHighestCardValueForCount(firstHand, 2);
        int secondHandPairValue = getHighestCardValueForCount(secondHand, 2);
        return Integer.compare(firstHandPairValue, secondHandPairValue);
    }

    private int breakTieForThreeOfAKind(Hand firstHand, Hand secondHand) {
        int firstHandThreeValue = getHighestCardValueForCount(firstHand, 3);
        int secondHandThreeValue = getHighestCardValueForCount(secondHand, 3);

        if (firstHandThreeValue != secondHandThreeValue) {
            return Integer.compare(firstHandThreeValue, secondHandThreeValue);
        }
        // Compare kickers
        return breakTieByHighestCard(firstHand, secondHand);
    }

    private int breakTieForTwoPairs(Hand firstHand, Hand secondHand) {
        Map<Integer, Long> firstHandPairValues = firstHand.getCardsWithSameValue().entrySet().stream()
            .filter(entry -> entry.getValue() == 2)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<Integer, Long> secondHandPairValues = secondHand.getCardsWithSameValue().entrySet().stream()
            .filter(entry -> entry.getValue() == 2)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        int firstHandHighPair = firstHandPairValues.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        int secondHandHighPair = secondHandPairValues.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);

        if (firstHandHighPair != secondHandHighPair) {
            return Integer.compare(firstHandHighPair, secondHandHighPair);
        }

        int firstHandLowPair = firstHandPairValues.keySet().stream().filter(k -> k != firstHandHighPair).mapToInt(Integer::intValue).max().orElse(0);
        int secondHandLowPair = secondHandPairValues.keySet().stream().filter(k -> k != secondHandHighPair).mapToInt(Integer::intValue).max().orElse(0);

        if (firstHandLowPair != secondHandLowPair) {
            return Integer.compare(firstHandLowPair, secondHandLowPair);
        }

        // Compare kicker
        return breakTieByHighestCard(firstHand, secondHand);
    }

    private int breakTieForPair(Hand firstHand, Hand secondHand) {
        int firstHandPairValue = getHighestCardValueForCount(firstHand, 2);
        int secondHandPairValue = getHighestCardValueForCount(secondHand, 2);

        if (firstHandPairValue != secondHandPairValue) {
            return Integer.compare(firstHandPairValue, secondHandPairValue);
        }
        // Compare kickers
        return breakTieByHighestCard(firstHand, secondHand);
    }

    public String compareAndGetResultMessage(Hand firstHand, Hand secondHand) {
        int compareResult = compare(firstHand, secondHand);

        return compareResult == 0 ? "It's a draw!" :
                compareResult > 0 ? String.format("First hand wins! (%s)", ranker.getRank(firstHand).getName())
                        : String.format("Second hand wins! (%s)", ranker.getRank(secondHand).getName());
    }
}

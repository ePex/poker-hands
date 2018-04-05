package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.service.rankers.FlushRanker;
import de.epex.pokerhands.service.rankers.FourOfAKindRanker;
import de.epex.pokerhands.service.rankers.FullHouseRanker;
import de.epex.pokerhands.service.rankers.HighCardRanker;
import de.epex.pokerhands.service.rankers.PairRanker;
import de.epex.pokerhands.service.rankers.Ranker;
import de.epex.pokerhands.service.rankers.RoyalFlushRanker;
import de.epex.pokerhands.service.rankers.StraightFlushRanker;
import de.epex.pokerhands.service.rankers.StraightRanker;
import de.epex.pokerhands.service.rankers.ThreeOfAKindRanker;
import de.epex.pokerhands.service.rankers.TwoPairRanker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class HandComparator implements Comparator<Hand> {

    private final List<Ranker> rankers = Arrays.asList(
            new RoyalFlushRanker(),
            new StraightFlushRanker(),
            new FourOfAKindRanker(),
            new FullHouseRanker(),
            new FlushRanker(),
            new StraightRanker(),
            new ThreeOfAKindRanker(),
            new TwoPairRanker(),
            new PairRanker(),
            new HighCardRanker()
    );

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return zero on draw negative value if first hand is less then second hand and positive value if first hand is higher then second hand
     */
    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Ranker ranker = getRanker(firstHand, secondHand);

        if (ranker.matches(firstHand) && ranker.matches(secondHand)) {
            ranker.compare(firstHand, secondHand);
        } else if (ranker.matches(firstHand)) {
            return 1;
        } else {
            return -1;
        }


        int valueFirstHand = 0;//ranker.getRank(firstHand).getValue();
        int valueSecondHand = 0;//ranker.getRank(secondHand).getValue();

        if (valueFirstHand == valueSecondHand) {
            Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
            Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

            // special case for full house
            if (Rank.FULL_HOUSE.getValue() == valueFirstHand) {
                valueFirstHand = firstHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
                valueSecondHand = secondHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
            }

            // max value of cards with same value (like three of a kind)
            if (valueFirstHand == valueSecondHand) {
                valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
                valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            }

            // sum of cards with same value (like a pair)
            if (valueFirstHand == valueSecondHand) {
                valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();
                valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();
            }

            if (valueFirstHand == valueSecondHand) {
                // highest card
                int index = 5;
                while (index > 0) {
                    index--;
                    valueFirstHand = firstHand.getCards().get(index).getValue();
                    valueSecondHand = secondHand.getCards().get(index).getValue();
                    if (firstHand.getCards().get(index).getValue() != secondHand.getCards().get(index).getValue()) {
                        break;
                    }
                }
            }
        }
        return Integer.compare(valueFirstHand, valueSecondHand);
    }

    private Ranker getRanker(Hand firstHand, Hand secondHand) {
        for (Ranker ranker : rankers) {
            if (ranker.matches(firstHand) || ranker.matches(secondHand)) {
                return ranker;
            }
        }
        return new HighCardRanker();
    }

    public String compareAndGetResultMessage(Hand firstHand, Hand secondHand) {
        int compareResult = compare(firstHand, secondHand);

        return "bla";
        /*
        return compareResult == 0 ? "It's a draw!" :
                compareResult > 0 ? String.format("First hand wins! (%s)", ranker.getRank(firstHand).getName())
                        : String.format("Second hand wins! (%s)", ranker.getRank(secondHand).getName());

        */

    }
}

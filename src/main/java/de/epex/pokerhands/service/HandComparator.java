package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;

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
        int valueFirstHand = ranker.getRank(firstHand).getValue();
        int valueSecondHand = ranker.getRank(secondHand).getValue();

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

    public String compareAndGetResultMessage(Hand firstHand, Hand secondHand) {
        int compareResult = compare(firstHand, secondHand);

        return compareResult == 0 ? "It's a draw!" :
                compareResult > 0 ? String.format("First hand wins! (%s)", ranker.getRank(firstHand).getName())
                        : String.format("First hand wins! (%s)", ranker.getRank(secondHand).getName());

    }
}

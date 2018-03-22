package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;

@Service
public class HandComparator implements Comparator<Hand> {

    private final Ranker ranker;

    public HandComparator(Ranker ranker) {
        this.ranker = ranker;
    }

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return null on draw otherwise winning hand
     */
    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        int valueFirstHand = ranker.getRank(firstHand).getValue();
        int valueSecondHand = ranker.getRank(secondHand).getValue();

        if (valueFirstHand == valueSecondHand) {
            Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
            Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

            // full house
            if (Rank.FULL_HOUSE.getValue() == valueFirstHand) {
                valueFirstHand = firstHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
                valueSecondHand = secondHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
            }

            // two pair
            if (valueFirstHand == valueSecondHand) {
                valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
                valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            }

            //one pair
            if (valueFirstHand == valueSecondHand) {
                valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();
                valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();
            }

            if (valueFirstHand == valueSecondHand) {
                // high card draw
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

}

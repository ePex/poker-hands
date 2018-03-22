package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Comparator {

    private final Ranker ranker;

    public Comparator(Ranker ranker) {
        this.ranker = ranker;
    }

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return null on draw otherwise winning hand
     */
    public Hand compare(Hand firstHand, Hand secondHand) {
        int valueFirstHand = ranker.getRank(firstHand).getValue();
        int valueSecondHand = ranker.getRank(secondHand).getValue();

        if (valueFirstHand == valueSecondHand) {
            Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
            Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

            if (Rank.FULL_HOUSE.getValue() == valueFirstHand) {
                valueFirstHand = firstHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
                valueSecondHand = secondHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
            }

            if (valueFirstHand == valueSecondHand) {
                valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
                valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            }

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
        return valueFirstHand == valueSecondHand ? null : valueFirstHand > valueSecondHand ? firstHand : secondHand;
    }

}

package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

import java.util.Map;

public class PairRanker extends HighCardRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasPair();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
        Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

        int valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();
        int valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).sum();

        if (valueFirstHand == valueSecondHand) {
            return super.compare(firstHand, secondHand);
        }

        return Integer.compare(valueFirstHand, valueSecondHand);
    }

}

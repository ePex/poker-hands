package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

import java.util.Map;

public class ThreeOfAKindRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasThreeOfAKind();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
        Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

        int valueFirstHand = firstHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        int valueSecondHand = secondHandPairs.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);

        return Integer.compare(valueFirstHand, valueSecondHand);
    }

}

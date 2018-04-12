package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

import java.util.Map;

public class FullHouseRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasFullHouse();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
        Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

        int valueFirstHand = firstHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();
        int valueSecondHand = secondHandPairs.entrySet().stream().filter(e -> e.getValue() == 3).mapToInt(Map.Entry::getKey).sum();

        return Integer.compare(valueFirstHand, valueSecondHand);
    }

}

package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Card;
import de.epex.pokerhands.service.model.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoPairRanker extends PairRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasTwoPair();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        int compare = 0;//super.compare(firstHand, secondHand);

        if (compare == 0) {
            compare = compareRemainingCard(firstHand, secondHand);
        }

        return compare;
    }

    private int compareRemainingCard(Hand firstHand, Hand secondHand) {
        Map<Integer, Long> firstHandPairs = firstHand.getCardsWithSameValue();
        Map<Integer, Long> secondHandPairs = secondHand.getCardsWithSameValue();

        List<Card> remainingCardFirstHand = firstHand.getCards()
                .stream()
                .filter(card -> !firstHandPairs.keySet().contains(card.getValue()))
                .collect(Collectors.toList());
        List<Card> remainingCardSecondHand = secondHand.getCards()
                .stream()
                .filter(card -> !secondHandPairs.keySet().contains(card.getValue()))
                .collect(Collectors.toList());

        return Integer.compare(remainingCardFirstHand.get(0).getValue(), remainingCardSecondHand.get(0).getValue());
    }

}

package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public class ThreeOfAKindRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasThreeOfAKind();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        return 0;
    }

}

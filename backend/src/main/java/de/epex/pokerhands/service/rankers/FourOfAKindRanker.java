package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public class FourOfAKindRanker extends ThreeOfAKindRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasFourOfAKind();
    }

}

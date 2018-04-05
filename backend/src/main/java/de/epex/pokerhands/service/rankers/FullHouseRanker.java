package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public class FullHouseRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasFullHouse();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        return 0;
    }

}

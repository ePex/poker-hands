package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public class StraightFlushRanker extends HighCardRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return hand.hasStraightFlush();
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        return 0;
    }

}

package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public class HighCardRanker implements Ranker {

    @Override
    public boolean matches(Hand hand) {
        return true;
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        return firstHand.getHighCard().compareTo(secondHand.getHighCard());
    }

}

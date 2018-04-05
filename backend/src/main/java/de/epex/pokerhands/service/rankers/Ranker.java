package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public interface Ranker {

    boolean matches(Hand hand);

    /**
     * this is executed once both hands have the same rank so we need to handle the draw situation
     *
     * @param firstHand
     * @param secondHand
     * @return
     */
    int compare(Hand firstHand, Hand secondHand);

}

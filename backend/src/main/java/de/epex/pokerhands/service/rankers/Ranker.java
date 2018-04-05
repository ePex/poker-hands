package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;

public interface Ranker {

    boolean matches(Hand hand);

    int compare(Hand firstHand, Hand secondHand);

}

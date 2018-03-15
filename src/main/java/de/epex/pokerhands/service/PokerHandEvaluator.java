package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.beans.factory.annotation.Autowired;

public class PokerHandEvaluator {

    private final HandRanker handRanker;

    @Autowired
    public PokerHandEvaluator(HandRanker handRanker) {
        this.handRanker = handRanker;
    }

    public String evaluate(String firstHandString, String secondHandString) {
        try {
            Hand firstHand = new Hand(firstHandString);
            Hand secondHand = new Hand(secondHandString);

            Rank rankFirstHand = handRanker.getRank(firstHand);
            Rank rankSecondHand = handRanker.getRank(secondHand);

            return rankFirstHand.getValue() > rankSecondHand.getValue()
                    ? String.format("First hand wins! (%s)", rankFirstHand.getName())
                    : String.format("Second hand wins! (%s)", rankSecondHand.getName());
        } catch (IllegalArgumentException e) {
            return "input data is invalid";
        }
    }

}

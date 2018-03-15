package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Evaluator {

    private final Ranker ranker;

    @Autowired
    public Evaluator(Ranker ranker) {
        this.ranker = ranker;
    }

    public String evaluate(String firstHandString, String secondHandString) {
        try {
            Hand firstHand = new Hand(firstHandString);
            Hand secondHand = new Hand(secondHandString);

            Rank rankFirstHand = ranker.getRank(firstHand);
            Rank rankSecondHand = ranker.getRank(secondHand);

            return rankFirstHand.equals(rankSecondHand) ? "It's a draw!" :
                    rankFirstHand.getValue() > rankSecondHand.getValue()
                    ? String.format("First hand wins! (%s)", rankFirstHand.getName())
                    : String.format("Second hand wins! (%s)", rankSecondHand.getName());
        } catch (IllegalArgumentException e) {
            return "Input data is invalid";
        }
    }

}

package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;

public class PokerHandEvaluator {

    public String evaluate(String firstHand, String secondHand) {
        try {
            Hand hand1 = new Hand(firstHand);
            Hand hand2 = new Hand(secondHand);
        } catch (IllegalArgumentException e) {
            return "input data is invalid";
        }

        // rank first hand

        // rank second hand

        // compare hand rankings

        // return result

        return "first";
    }

}

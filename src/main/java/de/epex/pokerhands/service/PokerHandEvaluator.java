package de.epex.pokerhands.service;

import org.springframework.beans.factory.annotation.Autowired;

public class PokerHandEvaluator {

    private final InputValidator inputValidator;

    @Autowired
    public PokerHandEvaluator(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String evaluate(String firstHand, String secondHand) {
        // parse strings
        if (inputIsNotValid(firstHand, secondHand)) {
            return "input data is invalid";
        }

        // rank first hand

        // rank second hand

        // compare hand rankings

        // return result

        return "first";
    }

    private boolean inputIsNotValid(String firstHand, String secondHand) {
        return !inputValidator.validate(firstHand) || !inputValidator.validate(secondHand);
    }

}

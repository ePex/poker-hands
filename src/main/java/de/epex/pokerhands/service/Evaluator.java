package de.epex.pokerhands.service;

import de.epex.pokerhands.service.exception.InvalidPokerHandException; // Added import
import de.epex.pokerhands.service.model.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Evaluator {

    private final HandComparator handComparator;

    @Autowired
    public Evaluator(HandComparator handComparator) {
        this.handComparator = handComparator;
    }

    public String evaluate(String firstHandString, String secondHandString) {
        try {
            Hand firstHand = new Hand(firstHandString);
            Hand secondHand = new Hand(secondHandString);

            return handComparator.compareAndGetResultMessage(firstHand, secondHand);
        } catch (InvalidPokerHandException e) { // Changed to catch InvalidPokerHandException
            throw e; // Re-throw the exception
        }
    }

}

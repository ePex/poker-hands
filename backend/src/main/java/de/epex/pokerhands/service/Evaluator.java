package de.epex.pokerhands.service;

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
        } catch (IllegalArgumentException e) {
            return "Input data is invalid";
        }
    }

}

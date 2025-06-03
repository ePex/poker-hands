package de.epex.pokerhands.service;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import de.epex.pokerhands.domain.Hand;
import de.epex.pokerhands.domain.HandComparator;
import de.epex.pokerhands.domain.ComparisonOutcome;
import de.epex.pokerhands.domain.Winner;
import de.epex.pokerhands.domain.Rank; // Added for clarity in test setup later, though not directly used by Evaluator
import org.springframework.stereotype.Service;

@Service
public class Evaluator {

    private final HandComparator handComparator;

    // Public constructor for Spring or general use
    public Evaluator() {
        this(new HandComparator());
    }

    // Package-private constructor for testing with a mock HandComparator
    Evaluator(HandComparator handComparator) {
        this.handComparator = handComparator;
    }

    public String evaluate(String firstHandString, String secondHandString) {
        try {
            Hand firstHand = new Hand(firstHandString);
            Hand secondHand = new Hand(secondHandString);

            ComparisonOutcome outcome = handComparator.determineWinner(firstHand, secondHand);

            switch (outcome.winner()) {
                case FIRST_HAND:
                    return String.format("First hand wins! (%s)", outcome.firstHandRank().getName());
                case SECOND_HAND:
                    return String.format("Second hand wins! (%s)", outcome.secondHandRank().getName());
                case DRAW:
                default:
                    // For a DRAW, the ranks are the same. It doesn't matter which rank name we use.
                    // However, ComparisonOutcome stores both, so we can pick one.
                    // If ranks could be different in a draw (not possible here), we'd need more logic.
                    return String.format("It's a draw! (Both %s)", outcome.firstHandRank().getName());
            }
        } catch (InvalidPokerHandException e) {
            throw e;
        }
    }

}

package de.epex.pokerhands.domain;

import de.epex.pokerhands.domain.Hand;
import de.epex.pokerhands.domain.Rank;
import de.epex.pokerhands.domain.HandEvaluationResult;
import de.epex.pokerhands.domain.Winner; // Added import
import de.epex.pokerhands.domain.ComparisonOutcome; // Added import

import java.util.Comparator;
import java.util.List;
import java.util.Objects; // Added for Objects.requireNonNull in determineWinner if needed, not strictly for this impl


public class HandComparator implements Comparator<Hand> {

    public HandComparator() {
    }

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Objects.requireNonNull(firstHand, "firstHand must not be null");
        Objects.requireNonNull(secondHand, "secondHand must not be null");

        HandEvaluationResult firstEval = firstHand.getEvaluationResult();
        HandEvaluationResult secondEval = secondHand.getEvaluationResult();

        Rank firstHandRank = firstEval.rank();
        Rank secondHandRank = secondEval.rank();

        if (firstHandRank != secondHandRank) {
            return Integer.compare(firstHandRank.getValue(), secondHandRank.getValue());
        }

        List<Integer> firstValues = firstEval.relevantCardValues();
        List<Integer> secondValues = secondEval.relevantCardValues();

        // Ensure lists are of the same size for comparison if rank is the same.
        // This should be guaranteed by Hand.calculateEvaluation() logic for a given rank.
        // If not, it might indicate an issue there or a need for more robust handling here.
        int size = Math.min(firstValues.size(), secondValues.size());

        for (int i = 0; i < size; i++) {
            int compare = Integer.compare(firstValues.get(i), secondValues.get(i));
            if (compare != 0) {
                return compare;
            }
        }

        // If one list of relevant values is longer than the other after all common elements are equal,
        // it doesn't necessarily mean a win/loss without poker-specific rules.
        // Typically, for the same rank, the number of tie-breaking values should be the same.
        // If sizes differ, it's a draw based on available tie-breakers.
        if (firstValues.size() != secondValues.size()) {
            // This scenario should ideally be clarified by poker rules for the specific rank.
            // For now, if common tie-breakers are equal, and one has more (e.g. more kickers considered),
            // it doesn't automatically make it a winner. Poker tie-breaking usually compares a fixed number of kickers.
            // So, if all compared kickers are equal, it's a draw.
        }

        return 0; // All relevant cards compared are equal, or sizes differ after common elements match.
    }

    public ComparisonOutcome determineWinner(Hand firstHand, Hand secondHand) {
        int comparisonResult = compare(firstHand, secondHand);
        Winner winner;
        if (comparisonResult > 0) {
            winner = Winner.FIRST_HAND;
        } else if (comparisonResult < 0) {
            winner = Winner.SECOND_HAND;
        } else {
            winner = Winner.DRAW;
        }
        // Ensure ranks are non-null before creating ComparisonOutcome
        Rank firstRank = Objects.requireNonNull(firstHand.getEvaluationResult().rank(), "First hand rank is null");
        Rank secondRank = Objects.requireNonNull(secondHand.getEvaluationResult().rank(), "Second hand rank is null");

        return new ComparisonOutcome(winner, firstRank, secondRank);
    }

    // Removed compareAndGetResultMessage method
}

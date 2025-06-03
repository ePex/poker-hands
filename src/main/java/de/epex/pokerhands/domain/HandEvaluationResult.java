package de.epex.pokerhands.domain;

import de.epex.pokerhands.domain.Rank; // Updated import for Rank
import java.util.List;
import java.util.Objects;

/**
 * Represents the evaluated result of a poker hand, including its rank
 * and the card values relevant for tie-breaking.
 * This is a Value Object.
 */
public record HandEvaluationResult(Rank rank, List<Integer> relevantCardValues) {

    /**
     * Canonical constructor for HandEvaluationResult.
     *
     * @param rank The rank of the hand.
     * @param relevantCardValues The ordered list of card values significant for this rank,
     *                           used for tie-breaking. Higher value cards should come first
     *                           if the order implies importance (e.g. for pairs, kickers).
     */
    public HandEvaluationResult {
        Objects.requireNonNull(rank, "rank must not be null");
        Objects.requireNonNull(relevantCardValues, "relevantCardValues must not be null");
        // For true immutability and to prevent external modification of the passed list,
        // it's good practice to make a defensive copy. List.copyOf is suitable here.
        relevantCardValues = List.copyOf(relevantCardValues);
    }

    // No explicit getters needed due to record providing rank() and relevantCardValues()
    // rank() returns the Rank enum instance
    // relevantCardValues() returns the List<Integer>
}

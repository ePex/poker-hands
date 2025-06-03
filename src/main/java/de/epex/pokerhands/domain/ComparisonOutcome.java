package de.epex.pokerhands.domain;

import java.util.Objects;

// Assuming Rank is already in de.epex.pokerhands.domain or accessible
// If Rank is still in service, the import would be de.epex.pokerhands.service.Rank

public record ComparisonOutcome(Winner winner, Rank firstHandRank, Rank secondHandRank) {
    public ComparisonOutcome {
        Objects.requireNonNull(winner, "winner must not be null");
        Objects.requireNonNull(firstHandRank, "firstHandRank must not be null");
        Objects.requireNonNull(secondHandRank, "secondHandRank must not be null");
    }
}

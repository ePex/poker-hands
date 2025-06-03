package de.epex.pokerhands.web.dto;

// No specific imports needed for a simple record unless annotations or other types were used.

public record CompareHandsDto(String firstHand, String secondHand) {
    // Record automatically provides:
    // 1. All-args constructor: public CompareHandsDto(String firstHand, String secondHand)
    // 2. Accessor methods: public String firstHand() and public String secondHand()
    // 3. equals(), hashCode(), and toString() implementations.
    // Removed Serializable as it's not explicitly required for this DTO for now.
}

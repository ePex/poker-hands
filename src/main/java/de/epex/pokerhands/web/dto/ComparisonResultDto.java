package de.epex.pokerhands.web.dto;

// No specific imports needed for a simple record.

public record ComparisonResultDto(String message) {
    // Record automatically provides:
    // 1. All-args constructor: public ComparisonResultDto(String message)
    // 2. Accessor method: public String message()
    // 3. equals(), hashCode(), and toString() implementations.
}

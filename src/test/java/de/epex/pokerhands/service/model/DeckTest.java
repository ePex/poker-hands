package de.epex.pokerhands.service.model;

import de.epex.pokerhands.domain.Card; // Use the domain Card
import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import org.junit.jupiter.api.Test; // JUnit 5

import static org.junit.jupiter.api.Assertions.*; // JUnit 5 Assertions

class DeckTest { // Renamed NewDeckTest to DeckTest

    @Test
    void getDeckSize_returns52() {
        assertEquals(52, Deck.getDeckSize());
    }

    @Test
    void isInDeck_withValidCardCreatedByFromString_returnsTrue() {
        // Card.fromString already validates against the deck.
        // So, if fromString succeeds, isInDeck must be true for that card.
        Card card = Card.fromString("AS"); // Ace of Spades
        assertTrue(Deck.isInDeck(card), "A standard card like Ace of Spades should be in the deck.");

        Card card2 = Card.fromString("2H"); // Two of Hearts, ValueSuite format
        assertTrue(Deck.isInDeck(card2), "A standard card like Two of Hearts should be in the deck.");
    }

    @Test
    void isInDeck_withManuallyCreatedNonStandardCard_returnsFalse() {
        // Test Deck.isInDeck directly with Card records that might not pass Card.fromString() validation,
        // but helps to ensure Deck.isInDeck logic itself is correct.
        // Note: Card.fromString is the primary way valid cards should be created for the application.

        // Card with value not in standard deck (e.g., 1 or 15)
        Card cardInvalidValueLow = new Card("S", 1); // Spade, value 1 (not standard)
        assertFalse(Deck.isInDeck(cardInvalidValueLow), "Card with value 1 should not be in deck.");

        Card cardInvalidValueHigh = new Card("H", 15); // Heart, value 15 (not standard)
        assertFalse(Deck.isInDeck(cardInvalidValueHigh), "Card with value 15 should not be in deck.");

        // Card with invalid suit
        Card cardInvalidSuit = new Card("X", 5); // Suit X, value 5
        assertFalse(Deck.isInDeck(cardInvalidSuit), "Card with suit X should not be in deck.");
    }

    @Test
    void fromString_forCardValueNotInDeck_throwsInvalidPokerHandException() {
        // This test confirms that Card.fromString, which uses Deck.isInDeck,
        // throws an exception for cards that are syntactically parseable but not standard.
        // Input "1S" will be rejected by Card.parseValue before Deck.isInDeck is called.
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> {
            Card.fromString("1S");
        });
        assertEquals("Invalid card value: '1'. Expected A,K,Q,J,T,10,9-2. Input was: '1S'", exception.getMessage());
    }

    @Test
    void fromString_forCardSuitNotInDeck_throwsInvalidPokerHandException() {
        // This test confirms that Card.fromString
        // throws an exception for cards with invalid suit characters.
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> {
            Card.fromString("5X"); // Value 5, Suit X (invalid)
        });
        // Message comes from Card.parseSuite
        assertTrue(exception.getMessage().contains("Invalid suite character: 'X'"));
    }
}

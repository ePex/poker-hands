package de.epex.pokerhands.domain;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NewCardTest {

    @Test
    void fromString_validCard_parsesCorrectly() {
        Card card = Card.fromString("AS");
        assertEquals("A", card.suite(), "Suite should be A"); // Note: Card.fromString converts suite to uppercase.
        assertEquals(14, card.value(), "Value for Ace should be 14");

        Card card2 = Card.fromString("h2");
        assertEquals("H", card2.suite(), "Suite should be H (uppercase)");
        assertEquals(2, card2.value(), "Value for 2 should be 2");

        Card cardT = Card.fromString("dT");
        assertEquals("D", cardT.suite(), "Suite should be D (uppercase)");
        assertEquals(10, cardT.value(), "Value for Ten should be 10");
    }

    @Test
    void fromString_validFaceCards_parsesCorrectly() {
        assertEquals(11, Card.fromString("CJ").value(), "Jack should be 11");
        assertEquals(12, Card.fromString("DQ").value(), "Queen should be 12");
        assertEquals(13, Card.fromString("HK").value(), "King should be 13");
    }

    @Test
    void internalToString_formatsCorrectly() {
        assertEquals("S5", Card.fromString("S5").internalToString());
        assertEquals("HA", Card.fromString("HA").internalToString());
        assertEquals("DT", Card.fromString("DT").internalToString());
        assertEquals("CJ", Card.fromString("CJ").internalToString());
        assertEquals("SQ", Card.fromString("SQ").internalToString());
        assertEquals("CK", Card.fromString("CK").internalToString());
    }

    @Test
    void recordEqualsAndHashCode_workAsExpected() {
        Card card1_S5 = Card.fromString("S5");
        Card card2_S5 = Card.fromString("s5"); // Different case for suite
        Card card3_H5 = Card.fromString("H5");
        Card card4_S6 = Card.fromString("S6");

        assertEquals(card1_S5, card2_S5, "Cards with same suite (case-insensitive) and value should be equal");
        assertEquals(card1_S5.hashCode(), card2_S5.hashCode(), "Hashcodes for equal cards should be same");

        assertNotEquals(card1_S5, card3_H5, "Cards with different suites should not be equal");
        assertNotEquals(card1_S5, card4_S6, "Cards with different values should not be equal");
    }

    @ParameterizedTest
    @ValueSource(strings = {"S1", "H15", "X5", "D0", "C111"}) // Invalid values or suits
    void fromString_invalidCardValueOrSuit_throwsInvalidPokerHandException(String cardStr) {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString(cardStr));
        // Check if message indicates invalidity, specific messages are in Card.fromString
        assertTrue(exception.getMessage().toLowerCase().contains("invalid") || exception.getMessage().contains("not in deck"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"S", "", "  ", "Suite10"}) // Invalid formats
    void fromString_invalidCardFormat_throwsInvalidPokerHandException(String cardStr) {
        assertThrows(InvalidPokerHandException.class, () -> Card.fromString(cardStr));
    }

    @Test
    void fromString_nullInput_throwsInvalidPokerHandException() {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString(null));
        assertEquals("Card string must be at least 2 characters long and a valid format (e.g., H2, DA). Input was: null", exception.getMessage());
    }

    // Test for card not in deck (if Deck.isInDeck is strict, e.g. "S1" would be caught here by current Card.fromString)
    @Test
    void fromString_cardValueSyntacticallyCorrectButNotInDeck_throwsException() {
        // Card.fromString -> Deck.isInDeck check will use the Card record (suite, value).
        // The current Deck.java initializes standard 52 cards. "S1" (value 1) is not standard.
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString("S1"));
        assertTrue(exception.getMessage().contains("Card(S1) is not in deck"));
    }
}

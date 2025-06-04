package de.epex.pokerhands.domain;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CardTest { // Renamed NewCardTest to CardTest to match filename

    @Test
    void fromString_validCard_parsesCorrectly() {
        // "AS" is Value: A, Suite: S
        Card card = Card.fromString("AS");
        assertEquals("S", card.suite(), "Suite should be S");
        assertEquals(14, card.value(), "Value for Ace should be 14");

        // "2H" is Value: 2, Suite: H
        Card card2 = Card.fromString("2h"); // Input "2h" for Value 2, Suite H
        assertEquals("H", card2.suite(), "Suite should be H (uppercase)");
        assertEquals(2, card2.value(), "Value for 2 should be 2");

        // "TD" is Value: T, Suite: D
        Card cardT = Card.fromString("Td"); // Input "Td" for Value T, Suite D
        assertEquals("D", cardT.suite(), "Suite should be D (uppercase)");
        assertEquals(10, cardT.value(), "Value for Ten should be 10");
    }

    @Test
    void fromString_validFaceCards_parsesCorrectly() {
        // ValueSuite format: JC (Jack of Clubs), QD (Queen of Diamonds), KH (King of Hearts)
        assertEquals(11, Card.fromString("JC").value(), "Jack should be 11");
        assertEquals("C", Card.fromString("JC").suite(), "Suite should be C");

        assertEquals(12, Card.fromString("QD").value(), "Queen should be 12");
        assertEquals("D", Card.fromString("QD").suite(), "Suite should be D");

        assertEquals(13, Card.fromString("KH").value(), "King should be 13");
        assertEquals("H", Card.fromString("KH").suite(), "Suite should be H");
    }

    @Test
    void internalToString_formatsCorrectly() {
        // Card.java's internalToString() produces ValueSuite format
        assertEquals("5S", Card.fromString("5S").internalToString());
        assertEquals("AH", Card.fromString("AH").internalToString());
        assertEquals("10D", Card.fromString("TD").internalToString()); // Expect "10D" for Ten of Diamonds
        assertEquals("JC", Card.fromString("JC").internalToString());
        assertEquals("QS", Card.fromString("QS").internalToString());
        assertEquals("KC", Card.fromString("KC").internalToString());
    }

    @Test
    void recordEqualsAndHashCode_workAsExpected() {
        Card card1_5S = Card.fromString("5S");
        Card card2_5S = Card.fromString("5s"); // Different case for suite
        Card card3_5H = Card.fromString("5H");
        Card card4_6S = Card.fromString("6S");

        assertEquals(card1_5S, card2_5S, "Cards with same suite (case-insensitive) and value should be equal");
        assertEquals(card1_5S.hashCode(), card2_5S.hashCode(), "Hashcodes for equal cards should be same");

        assertNotEquals(card1_5S, card3_5H, "Cards with different suites should not be equal");
        assertNotEquals(card1_5S, card4_6S, "Cards with different values should not be equal");
    }

    @ParameterizedTest
    // Inputs like "1S" (Value 1, Suite S) will be parsed but rejected by Deck.isInDeck
    // Inputs like "X5" (Value X, Suite 5) will be rejected by parseValue/parseSuite
    // "15H" (Value 15, Suite H) rejected by the 3-char length validation for non-"10" values.
    // "Z5" (Value Z, Suite 5) rejected by parseValue
    // "5Z" (Value 5, Suite Z) rejected by parseSuite
    @ValueSource(strings = {"1S", "15H", "X5", "Z5", "5Z", "D0"}) // "C111" moved to format test
    void fromString_invalidCardValueOrSuit_throwsInvalidPokerHandException(String cardStr) {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString(cardStr));
        String msg = exception.getMessage().toLowerCase();
        // These inputs should fail due to value/suite content, not format length.
        // Messages like "Invalid card value...", "Invalid suite character...", "Card(...) is not a standard playing card..."
        assertTrue(msg.contains("invalid") || msg.contains("not a standard playing card"),
                   "Exception message should contain 'invalid' or 'not a standard playing card'. Actual: " + exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"S", "", "  ", "ValueSuiteTooLong", "C111"}) // Invalid formats (length)
    void fromString_invalidCardFormat_throwsInvalidPokerHandException(String cardStr) {
        assertThrows(InvalidPokerHandException.class, () -> Card.fromString(cardStr));
    }

    @Test
    void fromString_nullInput_throwsInvalidPokerHandException() {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString(null));
        // Align with the actual message from Card.java for null/empty input
        assertEquals("Card string cannot be null or empty.", exception.getMessage());
    }

    // Test for card not in deck (if Deck.isInDeck is strict)
    @Test
    void fromString_cardValueSyntacticallyCorrectButNotInDeck_throwsException() {
        // Input "1S" (Value 1, Suite S) is syntactically valid for ValueSuite,
        // but value 1 is not a standard playing card value.
        // Card.java's fromString calls Deck.isInDeck.
        // The message from Card.java is: String.format("Card(%s) is not a standard playing card or is invalid. Input was: '%s'", cardToValidate.internalToString(), cardString)
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> Card.fromString("1S")); // Changed "S1" to "1S"
        // Value 1 is parsed by parseValue, but rejected as not A,K,Q,J,T,2-9.
        // The message comes from parseValue's throw.
        String expectedMessage = "Invalid card value: '1'. Expected A,K,Q,J,T,10,9-2. Input was: '1S'";
        assertEquals(expectedMessage, exception.getMessage());
    }
}

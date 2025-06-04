package de.epex.pokerhands.domain;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import de.epex.pokerhands.service.model.Deck; // Ensure Deck is accessible and functional

public record Card(String suite, int value) {

    public static Card fromString(String cardString) {
        if (cardString == null || cardString.trim().isEmpty()) {
            throw new InvalidPokerHandException("Card string cannot be null or empty.");
        }
        String trimmedCardString = cardString.trim().toUpperCase(); // Normalize case early

        if (trimmedCardString.length() < 2 || trimmedCardString.length() > 3) {
            throw new InvalidPokerHandException("Card string must be 2 or 3 characters long (e.g., AS, 10D, 5H). Input was: '" + cardString + "'");
        }

        String valueStrInput;
        String suiteCharInput;

        // Correctly assign value and suite parts for "ValueSuite"
        // Suite is the LAST character
        suiteCharInput = trimmedCardString.substring(trimmedCardString.length() - 1);
        // Value is everything BEFORE the last character
        valueStrInput = trimmedCardString.substring(0, trimmedCardString.length() - 1);

        // Validate 3-character inputs (must be "10" for value)
        if (trimmedCardString.length() == 3 && !valueStrInput.equals("10")) {
            throw new InvalidPokerHandException("Invalid 3-character card string. Value part must be '10' (e.g., 10S, 10D). Input was: '" + cardString + "'");
        }

        if (valueStrInput.equals("10") && trimmedCardString.length() == 2) { // e.g. "10" with no suite
             throw new InvalidPokerHandException("Card string '10' is incomplete, missing suite character (e.g., 10S). Input was: '" + cardString + "'");
        }

        String parsedSuite = parseSuite(suiteCharInput.charAt(0), cardString); // suiteCharInput is a single char string
        int parsedValue = parseValue(valueStrInput, cardString);

        Card cardToValidate = new Card(parsedSuite, parsedValue);

        if (!Deck.isInDeck(cardToValidate)) { // Assumes Deck.isInDeck works correctly
            throw new InvalidPokerHandException(String.format("Card(%s) is not a standard playing card or is invalid. Input was: '%s'", cardToValidate.internalToString(), cardString));
        }
        return cardToValidate;
    }

    private static String parseSuite(char suiteChar, String originalCardStr) {
        // suiteChar is already from an uppercased string part
        if ("CDHS".indexOf(suiteChar) == -1) {
            throw new InvalidPokerHandException("Invalid suite character: '" + suiteChar + "'. Valid suites are C, D, H, S. Input was: '" + originalCardStr + "'");
        }
        return String.valueOf(suiteChar);
    }

    private static int parseValue(String valueStr, String originalCardStr) {
        // valueStr is already uppercased
        switch (valueStr) {
            case "A": return 14;
            case "K": return 13;
            case "Q": return 12;
            case "J": return 11;
            case "T": return 10;
            case "10": return 10; // Handles the "10" value string
            default:
                // For single character values "2" through "9"
                if (valueStr.length() == 1) {
                    try {
                        int val = Integer.parseInt(valueStr);
                        if (val >= 2 && val <= 9) {
                            return val;
                        }
                    } catch (NumberFormatException e) {
                        // Fall through to the generic error for invalid value
                    }
                }
                throw new InvalidPokerHandException("Invalid card value: '" + valueStr + "'. Expected A,K,Q,J,T,10,9-2. Input was: '" + originalCardStr + "'");
        }
    }

    /**
     * Returns a string representation of the card in ValueSuite format (e.g., "AS", "10D").
     */
    public String internalToString() {
        String stringValue;
        switch (value) {
            case 14: stringValue = "A"; break;
            case 13: stringValue = "K"; break;
            case 12: stringValue = "Q"; break;
            case 11: stringValue = "J"; break;
            case 10: stringValue = "10"; break; // Consistent with "10" as input value
            default: stringValue = String.valueOf(value); break;
        }
        return stringValue + suite; // Value then Suite
    }
}

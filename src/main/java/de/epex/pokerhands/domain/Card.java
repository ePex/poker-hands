package de.epex.pokerhands.domain; // Updated package

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
// Assuming Deck will be updated to refer to domain.Card or its logic moved/refactored.
// For now, this import implies Deck is still in service.model or its isInDeck is static and accessible.
import de.epex.pokerhands.service.model.Deck;
import java.util.Objects;

public record Card(String suite, int value) {

    public static Card fromString(String cardString) {
        if (cardString == null || cardString.length() < 2) {
            throw new InvalidPokerHandException("Card string must be at least 2 characters long and a valid format (e.g., H2, DA). Input was: " + cardString);
        }

        String suite = getSuiteFromCardStringStatic(cardString);
        int value = getValueFromCardStringStatic(cardString);

        Card cardToValidate = new Card(suite, value);
        // Deck.isInDeck will need to be aware of the new Card location if it's not already.
        if (!Deck.isInDeck(cardToValidate)) {
            throw new InvalidPokerHandException(String.format("Card(%s) is not in deck. Input was: %s", cardToValidate.internalToString(), cardString));
        }
        return cardToValidate;
    }

    private static String getSuiteFromCardStringStatic(String card) {
        char suiteChar = card.charAt(0);
        if ("CDHScdhs".indexOf(suiteChar) == -1) {
            throw new InvalidPokerHandException("Invalid suite character: " + suiteChar + ". Valid suites are C, D, H, S.");
        }
        return String.valueOf(suiteChar).toUpperCase();
    }

    private static int getValueFromCardStringStatic(String card) {
        String stringValue = card.substring(1).toUpperCase();
        switch (stringValue) {
            case "T": return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 14;
            default:
                try {
                    int val = Integer.parseInt(stringValue);
                    if (val < 2 || val > 9) {
                        throw new InvalidPokerHandException("Invalid card value: " + stringValue + ". Value must be between 2-9 for numeric cards.");
                    }
                    return val;
                } catch (NumberFormatException e) {
                    throw new InvalidPokerHandException("Invalid card value: " + stringValue + ". Not a recognized card value.", e);
                }
        }
    }

    public String internalToString() {
        String stringValue;
        switch (value) {
            case 10: stringValue = "T"; break;
            case 11: stringValue = "J"; break;
            case 12: stringValue = "Q"; break;
            case 13: stringValue = "K"; break;
            case 14: stringValue = "A"; break;
            default: stringValue = String.valueOf(value); break;
        }
        return suite + stringValue;
    }

}

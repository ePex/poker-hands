package de.epex.pokerhands.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputValidator {

    private List<String> legalValues = new ArrayList<>();

    InputValidator() {
        List<String> suites = Arrays.asList("C", "D", "H", "S");
        List<String> values = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");

        //TODO refactor this to a stream operation
        for (String suite : suites) {
            for (String value : values) {
                legalValues.add(suite + value);
            }
        }
    }

    public boolean validate(String hand) {
        String[] cards = hand.split("\\s");
        if (cards.length < 5 || cards.length > 5) {
            return false;
        }

        //TODO refactor to stream operation
        for (String card : cards) {
            if (isIllegalValue(card)) {
                return false;
            }
        }

        return true;
    }

    private boolean isIllegalValue(String card) {
        return legalValues.stream().noneMatch(s -> s.equalsIgnoreCase(card));
    }

}

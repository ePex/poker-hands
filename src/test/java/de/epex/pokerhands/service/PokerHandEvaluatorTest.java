package de.epex.pokerhands.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PokerHandEvaluatorTest {

    private final static String FIRST_INPUT_OK = "C5 D3 D4 S7 C6";
    private final static String SECOND_INPUT_OK = "DA D3 D5 H8 S8";
    private final static String INPUT_NOT_OK = "S2 C7 HA D1 S9";

    private final InputValidator inputValidator = new InputValidator();

    private final PokerHandEvaluator classUnderTest = new PokerHandEvaluator(inputValidator);

    @Test
    public void testEvaluateFirstHandWinsWithStraight() {
        String result = classUnderTest.evaluate(FIRST_INPUT_OK, SECOND_INPUT_OK);
        assertThat(result, is("first"));
    }

    @Test
    public void testEvaluateWithInvalidInputData() {
        String result = classUnderTest.evaluate(FIRST_INPUT_OK, INPUT_NOT_OK);
        assertThat(result, is("input data is invalid"));
    }
}
package de.epex.pokerhands.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EvaluatorTest {

    private final static String FIRST_INPUT_OK = "C5 D3 D4 S7 C6";
    private final static String SECOND_INPUT_OK = "DA D3 D5 H8 S8";
    private final static String INPUT_NOT_OK = "S2 C7 HA D1 S9";

    private final Evaluator classUnderTest = new Evaluator(new HandComparator(new Ranker()));

    @Test
    public void testEvaluateFirstHandWinsWithStraight() {
        String result = classUnderTest.evaluate(FIRST_INPUT_OK, SECOND_INPUT_OK);
        assertThat(result, is("First hand wins! (Straight)"));
    }

    @Test
    public void testEvaluateSecondHandWinsWithStraight() {
        String result = classUnderTest.evaluate(SECOND_INPUT_OK, FIRST_INPUT_OK);
        assertThat(result, is("Second hand wins! (Straight)"));
    }

    @Test
    public void testEvaluateWithInvalidInputData() {
        String result = classUnderTest.evaluate(FIRST_INPUT_OK, INPUT_NOT_OK);
        assertThat(result, is("Input data is invalid"));
    }

    @Test
    public void testEvaluateDraw() {
        String result = classUnderTest.evaluate("S3 D7 S9 HJ CA", "S3 H7 D9 DJ DA");
        assertThat(result, is("It's a draw!"));
    }

}
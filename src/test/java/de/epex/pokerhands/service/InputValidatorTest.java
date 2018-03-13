package de.epex.pokerhands.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InputValidatorTest {

    private final static String INPUT_TO_SHORT = "S2 C7 HA D9";
    private final static String INPUT_TO_LONG = "S2 C7 HA D9 D2 H8";


    private final static String INPUT_OK = "C5 D3 D4 S7 C6";
    private final static String INPUT_NOT_OK = "S2 C7 HA D1 S9";

    private final InputValidator classUnderTest = new InputValidator();

    @Test
    public void testInputToShort() {
        boolean result = classUnderTest.validate(INPUT_TO_SHORT);
        assertThat(result, is(false));
    }

    @Test
    public void testInputToLong() {
        boolean result = classUnderTest.validate(INPUT_TO_LONG);
        assertThat(result, is(false));
    }

    @Test
    public void testInputLengthOk() {
        boolean result = classUnderTest.validate(INPUT_OK);
        assertThat(result, is(true));
    }

    @Test
    public void testInputNotOk() {
        boolean result = classUnderTest.validate(INPUT_NOT_OK);
        assertThat(result, is(false));
    }

    // TODO validate that this list has a size of 52

}
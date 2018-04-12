package de.epex.pokerhands.web.controller;


import de.epex.pokerhands.service.HandComparator;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PokerHandsRestControllerTest {

    private final static String FIRST_INPUT_OK = "C5 D3 D4 S7 C6";
    private final static String SECOND_INPUT_OK = "DA D3 D5 H8 S8";
    private final static String INPUT_NOT_OK = "S2 C7 HA D1 S9";

    private final PokerHandsRestController classUnderTest = new PokerHandsRestController(new HandComparator());
    private final CompareHandsDto compareHandsDto = new CompareHandsDto();


    @Test
    public void testEvaluateFirstHandWinsWithStraight() {
        compareHandsDto.setFirstHand(FIRST_INPUT_OK);
        compareHandsDto.setSecondHand(SECOND_INPUT_OK);

        ComparisonResultDto result = classUnderTest.compareHands(compareHandsDto);

        assertThat(result.getMessage(), is("First hand wins! (Straight)"));
    }

    @Test
    public void testEvaluateSecondHandWinsWithStraight() {
        compareHandsDto.setFirstHand(SECOND_INPUT_OK);
        compareHandsDto.setSecondHand(FIRST_INPUT_OK);

        ComparisonResultDto result = classUnderTest.compareHands(compareHandsDto);

        assertThat(result.getMessage(), is("Second hand wins! (Straight)"));
    }

    @Test
    public void testEvaluateWithInvalidInputData() {
        compareHandsDto.setFirstHand(FIRST_INPUT_OK);
        compareHandsDto.setSecondHand(INPUT_NOT_OK);

        ComparisonResultDto result = classUnderTest.compareHands(compareHandsDto);

        assertThat(result.getMessage(), is("Input data is invalid: 'Card(D1) is not in deck'"));
    }

    @Test
    public void testEvaluateDraw() {
        compareHandsDto.setFirstHand("S3 D7 S9 HJ CA");
        compareHandsDto.setSecondHand("S3 H7 D9 DJ DA");

        ComparisonResultDto result = classUnderTest.compareHands(compareHandsDto);

        assertThat(result.getMessage(), is("It's a draw!"));
    }

}
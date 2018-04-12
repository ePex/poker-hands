package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class StraightRankerTest {

    private StraightRanker classUnderTest = new StraightRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("S3 D4 S5 H6 C7");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void matchesWithSymbols() {
        Hand hand = new Hand("S8 D9 S10 HJ CQ");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void testCompareStraightDraw() {
        Hand firstHand = new Hand("S3 C4 H5 S6 D7");
        Hand secondHand = new Hand("D5 C6 H7 S8 D9");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }
}
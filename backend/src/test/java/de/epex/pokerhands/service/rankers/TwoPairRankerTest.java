package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class TwoPairRankerTest {

    private TwoPairRanker classUnderTest = new TwoPairRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("S4 D8 H8 CJ SJ");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void compareTwoPairDrawHighestPairWins() {
        Hand firstHand = new Hand("S4 D8 H8 CJ SJ");
        Hand secondHand = new Hand("S3 H3 D9 DK CK");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

    @Test
    public void compareTwoPairDrawSecondHighestPairWins() {
        Hand firstHand = new Hand("S4 D8 H8 CJ SJ");
        Hand secondHand = new Hand("S3 H3 D9 DJ HJ");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

    @Test
    public void compareTwoPairDrawRemainingHighestCardWins() {
        Hand firstHand = new Hand("S10 H10 S7 H2 D2");
        Hand secondHand = new Hand("S2 H2 D9 D10 C10");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

    @Test
    public void compareTwoPairDrawRealDraw() {
        Hand firstHand = new Hand("S3 C3 S7 H2 D2");
        Hand secondHand = new Hand("S2 H2 D9 D10 C10");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }
}